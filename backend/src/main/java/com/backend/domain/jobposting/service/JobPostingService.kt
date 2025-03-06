package com.backend.domain.jobposting.service;

import com.backend.domain.jobposting.dto.JobPostingDetailResponse
import com.backend.domain.jobposting.dto.JobPostingPageResponse
import com.backend.domain.jobposting.repository.JobPostingRepository
import com.backend.domain.jobposting.util.JobPostingSearchCondition
import com.backend.domain.user.entity.SiteUser
import com.backend.global.exception.GlobalErrorCode
import com.backend.global.exception.GlobalException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * JobPostingService
 * <p>채용 공고 서비스 입니다.</p>
 *
 * @author Kim Dong O
 */
@Service
class JobPostingService(private val jobPostingRepository: JobPostingRepository) {

	/**
	 * 채용 공고 동적 페이징 조회 메서드 입니다.
	 *
	 * @param jobPostingSearchCondition 조회 조건 객체 {@link JobPostingSearchCondition}
	 * @return {@link Page<JobPostingPageResponse>}
	 */
	@Transactional(readOnly = true)
	fun findAll(jobPostingSearchCondition: JobPostingSearchCondition)
	: Page<JobPostingPageResponse> {
		val pageNum = jobPostingSearchCondition.pageNum ?: 0
		val pageSize = jobPostingSearchCondition.pageSize ?: 10

		val pageable = PageRequest.of(pageNum, pageSize)

		return jobPostingRepository.findAll(jobPostingSearchCondition, pageable)
	}

	/**
	 * 채용 공고 상세 조회 메서드 입니다.
	 *
	 * @param jobPostingId 채용 공고 ID
	 * @param siteUserId   회원 ID
	 * @return {@link JobPostingDetailResponse}
	 */
	@Transactional(readOnly = true)
	fun findDetailById(jobPostingId: Long, siteUserId: Long): JobPostingDetailResponse {

		return jobPostingRepository.findDetailById(jobPostingId, siteUserId)
			.orElseThrow { GlobalException(GlobalErrorCode.JOB_POSTING_NOT_FOUND) }
	}

	fun findAllVoter(
		jobPostingSearchCondition: JobPostingSearchCondition,
		siteUser: SiteUser
	): Page<JobPostingPageResponse> {

		val pageNum = jobPostingSearchCondition.pageNum ?: 0
		val pageSize = jobPostingSearchCondition.pageSize ?: 10

		val pageable = PageRequest.of(pageNum, pageSize)

		return jobPostingRepository.findAllVoter(jobPostingSearchCondition, siteUser.id!!, pageable)
	}
}
