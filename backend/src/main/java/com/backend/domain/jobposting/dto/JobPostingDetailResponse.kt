package com.backend.domain.jobposting.dto

import com.backend.domain.jobposting.entity.ExperienceLevel
import com.backend.domain.jobposting.entity.JobPostingStatus
import com.backend.domain.jobposting.entity.RequireEducate
import com.backend.domain.jobposting.entity.Salary
import com.backend.domain.jobskill.dto.JobSkillResponse
import com.querydsl.core.annotations.QueryProjection
import java.time.ZonedDateTime

/**
 * JobPostingDetailResponse
 * <p>채용 공고 상세 조회 응답 객체 입니다.</p>
 *
 * @param id               id
 * @param subject          제목
 * @param url              공고 Url
 * @param postDate         작성 날짜
 * @param openDate         공개 날짜
 * @param closeDate        마감 날짜
 * @param companyName      회사 이름
 * @param companyLink      회사 Url
 * @param experienceLevel  직무 경력
 * @param requireEducate   학력
 * @param jobPostingStatus 공고 상태
 * @param salary           연봉
 * @param jobSkillList     직무 스킬
 * @param applyCnt         지원자 수
 * @param voterCount       추천 수
 * @param isVoter          추천 여부
 * @author Kim Dong O
 */
@JvmRecord
data class JobPostingDetailResponse @QueryProjection constructor(
    val id: Long,
    val subject: String, // 제목
    val url: String, // url

    val postDate: ZonedDateTime, // 작성 날짜
    val openDate: ZonedDateTime, // 공개 날짜
    val closeDate: ZonedDateTime, // 마감 날짜

    val companyName: String, // 회사 이름
    val companyLink: String, // 회사 링크

    val experienceLevel: ExperienceLevel, // 직무 경력
    val requireEducate: RequireEducate, // 학력
    val jobPostingStatus: JobPostingStatus, // 공고 상태
    val salary: Salary, // 연봉

    val jobSkillList: List<JobSkillResponse> = emptyList(), // 직무 스킬
    val applyCnt: Long, // 지원자 수
    val voterCount: Long, // 추천 수
    val isVoter: Boolean // 추천 여부
)
