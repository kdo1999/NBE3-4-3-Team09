package com.backend.domain.jobposting.repository;

import com.backend.domain.jobposting.dto.JobPostingDetailResponse
import com.backend.domain.jobposting.dto.JobPostingPageResponse
import com.backend.domain.jobposting.entity.JobPosting
import com.backend.domain.jobposting.util.JobPostingSearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * JobPostingRepository
 * <p>JobPosting 리포지토리 입니다.</p>
 *
 * @author Kim Dong O
 */
interface JobPostingRepository {

	/**
	 * @param id JobPosting id
	 * @return {@link Optional<JobPosting>}
	 * @implSpec Id 값으로 조회 메서드 입니다.
	 */
	fun findById(id: Long): JobPosting?

	/**
	 * @param jobPostingId JobPosting id
	 * @param siteUserId   SiteUser id
	 * @return {@link Optional<JobPostingDetailResponse>}
	 * @implSpec jobPostingId, siteUserId 값으로 조회 메서드 입니다.
	 */
	fun findDetailById(jobPostingId: Long, siteUserId: Long): JobPostingDetailResponse?


	/**
	 * @param jobPosting JobPosting 객체
	 * @return {@link JobPosting}
	 * @implSpec JobPosting 저장 메서드 입니다.
	 */
	fun save(jobPosting: JobPosting): JobPosting

	/**
	 * @return {@link List<JobPosting>}
	 * @implSpec JobPosting 전체 조회 메서드 입니다.
	 */
	fun findAll(): List<JobPosting>

	fun saveAll(publicDataList: List<JobPosting>): List<JobPosting>

	fun findIdsAll(): List<Long>

	/**
	 * @param jobPostingSearchCondition 검색 조건 객체
	 * @param pageable pageable
	 * @return {@link Page<JobPostingPageResponse>}
	 * @implSpec JobPosting 페이징 동적 조회 메서드 입니다.
	 */
	fun findAll(jobPostingSearchCondition: JobPostingSearchCondition, pageable: Pageable)
	: Page<JobPostingPageResponse>

	/**
	 * @param jobPostingId 채용 공고 ID
	 * @return Boolean - 데이터가 있으면 true, 없으면 false를 반환합니다.
	 * @implSpec JobPosting 중복 체크 메서드 입니다.
	 */
	fun existsById(jobPostingId: Long): Boolean

	/**
	 * @param jobPostingSearchCondition 검색 조건 객체
	 * @param siteUserId 추천한 사용자 ID
	 * @param pageable pageable
	 * @return {@link Page<JobPostingPageResponse>}
	 * @implSpec 사용자가 추천한 게시글을 조회하는 메서드 입니다.
	 */
	fun findAllVoter(
		jobPostingSearchCondition:
		JobPostingSearchCondition,
		siteUserId: Long,
		pageable: Pageable
	): Page<JobPostingPageResponse>
}
