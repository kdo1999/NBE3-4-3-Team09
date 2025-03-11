package com.backend.global.scheduler.service

import com.backend.domain.jobposting.entity.JobPosting
import com.backend.domain.jobposting.entity.JobPostingJobSkill
import com.backend.domain.jobposting.repository.JobPostingRepository
import com.backend.domain.jobskill.entity.JobSkill
import com.backend.global.exception.GlobalErrorCode
import com.backend.global.exception.GlobalException
import com.backend.global.redis.repository.RedisRepository
import com.backend.global.scheduler.apiresponse.Jobs
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class JobPostingSchedulerService(
    private val jobPostingRepository: JobPostingRepository,
    private val objectMapper: ObjectMapper,
    private val restTemplate: RestTemplate,
    private val redisRepository: RedisRepository
) {
    // URI로 조합할 OPEN API URL
    private val API_URL = "https://oapi.saramin.co.kr/job-search"

    // URI로 조합할 apiKey
    @Value("\${api.key}")
    private val apiKey: String? = null

    // URI로 조합할 한 페이지당 가져올데이터 수
    @Value("\${api.count}")
    private val count: Int? = null

    // 코루틴의 범위를 정의
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /**
     * 매일 자정(00:00)에 실행될 스케줄러 메서드입니다.
     *
     *
     * - 코루틴을 실행해서 processJobPostings를 호출합니다.
     */
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    @Transactional
    fun savePublicData() {
        coroutineScope.launch(Dispatchers.IO) {
            val pageNumber = 0
            val totalCount = 0
            val totalJobs = Int.Companion.MAX_VALUE

            processJobPostings(totalCount, totalJobs, pageNumber)
        }
    }

    /**
     * - 클래스 내에서 핵심로직이며, fetchJobPostings() 메소드를 통해 가져온 채용공고 데이터들을 저장하기위한 List<JobPosting>,
     * List<JobSkill> 로 변환하여, 저장하도록 하는 메서드이다. - 오늘 가져올수있는 총 공고수(totalJobs) 보다 데이텁베이스에 저장된
     * 공고수(totalCount) 크면 callBack 함수가 멈춘다.
     *
     * @param totalCount 현재 저장된 공고수
     * @param totalJobs  오늘 총 공고 수
     * @param pageNumber 현재 페이지 번호
    </JobSkill></JobPosting> */
    private suspend fun processJobPostings(totalCount: Int, totalJobs: Int, pageNumber: Int) {
        var totalCount = totalCount
        var totalJobs = totalJobs
        var pageNumber = pageNumber

        // api를 통해 채용 공고 데이터를 가져오기
        val jobs = fetchJobPostings(pageNumber, count!!)

        // JobPosting 클래스로 담기
        val jobPostingList = jobs.jobsDetail?.jobList?.map { it.toEntity() } ?: emptyList()

        //JSON 응답 파싱
        val jobMap = jobs.jobsDetail?.jobList?.associateBy { it.id.toLong() } ?: emptyMap()

        // 가져온 데이터를 리스트의 형태로 변환
        jobPostingList.forEach { jobPosting ->
            val findJob = jobMap[jobPosting.jobId]
            findJob?.positionDto?.jobCode?.code?.split(",")?.forEach { code ->
                val key = "JOB_SKILL_$code"

                // redis에 정보가 있는지 조회
                if (redisRepository.hasKey(key)) {
                    val jobSkillId = redisRepository.get(key)?.toString()?.toLongOrNull()
                    jobSkillId?.let {
                        jobPosting.addJobPostingJobSkill(JobPostingJobSkill(jobPosting, JobSkill(it)))
                    }
                }
            }
        }

        // 가공된 데이터들을 db에 저장
        val savedJobPostingList = saveNewJobs(jobPostingList.toMutableList())

        // 재귀호출을 통해 모든 데이터를 가져올 때까지 반복
        if (totalCount + savedJobPostingList.size < totalJobs) {
            processJobPostings(totalCount + savedJobPostingList.size, totalJobs, pageNumber + 1)
        }
    }

    /**
     * 지정된 페이지 번호와 가져올 데이터 개수를 기준으로 채용공고 데이터를 가져오는 메서드입니다.
     *
     *
     * @param pageNumber 현재 페이지 번호
     * @param count      가져올 데이터 개수
     */
    private suspend fun fetchJobPostings(pageNumber: Int, count: Int): Jobs {
        val uri = withContext(Dispatchers.IO) {
            UriComponentsBuilder.fromUriString(API_URL)
                .queryParam("access-key", apiKey)
                .queryParam("published", publishedDate)
                .queryParam("job_mid_cd", "2")
                .queryParam("start", pageNumber)
                .queryParam("count", count)
                .queryParam("fields", "count")
                .build()
                .encode()
                .toUri()
        }

        return withContext(Dispatchers.IO) {
            try {
                val jsonResponse = restTemplate.getForObject(uri, String::class.java)
                objectMapper.readValue(jsonResponse, Jobs::class.java)
            } catch (e: Exception) {
                throw GlobalException(GlobalErrorCode.JSON_PARSING_FAILED)
            }
        }
    }

    private val publishedDate: String
        /**
         * scheduler가 자정에 실행되기 때문에 전날 데이터를 가져오게 만든 메서드
         */
        get() {
            // 전날데이터
            val today = LocalDate.now().minusDays(1)
            val formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return today.format(formatter)
        }

    /**
     * JobPosting, JobSkill 데이터들을 데이터베이스에 저장하기위한 메서드
     *
     * @param newJobs 가공된 JobPosting 데이터 리스트
     */
    private suspend fun saveNewJobs(newJobs: List<JobPosting>): List<JobPosting> {
        /**
         * 데이터들을 비동기적으로 db에 저장합니다.
         */
        return withContext(Dispatchers.IO) {
            try {
                jobPostingRepository.saveAll(newJobs)
            } catch (e: Exception) {
                throw GlobalException(GlobalErrorCode.DATABASE_SAVE_FAILED)
            }
        }
    }
}
