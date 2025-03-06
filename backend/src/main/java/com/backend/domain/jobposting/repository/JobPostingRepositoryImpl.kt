package com.backend.domain.jobposting.repository;

import com.backend.domain.jobposting.dto.JobPostingDetailResponse
import com.backend.domain.jobposting.dto.JobPostingPageResponse
import com.backend.domain.jobposting.entity.JobPosting
import com.backend.domain.jobposting.util.JobPostingSearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class JobPostingRepositoryImpl(
	private val jobPostingJpaRepository: JobPostingJpaRepository,
	private val jobPostingQueryRepository: JobPostingQueryRepository
) :JobPostingRepository {


	override fun findById(id: Long): JobPosting? {
		return jobPostingJpaRepository.findByIdOrNull(id)
	}

	override fun findDetailById(jobPostingId: Long, siteUserId: Long): JobPostingDetailResponse? {
		return jobPostingQueryRepository.findDetailById(jobPostingId, siteUserId)
	}

	override fun save(jobPosting: JobPosting): JobPosting {
		return jobPostingJpaRepository.save(jobPosting)
	}

	@Override
	override fun findAll(): List<JobPosting> {
		return jobPostingJpaRepository.findAll()
	}

	@Override
	override fun findAll(
		jobPostingSearchCondition: JobPostingSearchCondition,
		pageable: Pageable
	): Page<JobPostingPageResponse> {
		return jobPostingQueryRepository.findAll(jobPostingSearchCondition, pageable);
	}

	@Override
	override fun existsById(jobPostingId: Long): Boolean {
		return jobPostingJpaRepository.existsById(jobPostingId);
	}

	@Override
	override fun saveAll(publicDataList: List<JobPosting>): List<JobPosting> {
		return jobPostingJpaRepository.saveAll(publicDataList);
	}

	@Override
	override fun findIdsAll(): List<Long> {
		return jobPostingJpaRepository.findIdsAll();
	}

	@Override
	override fun findAllVoter(
		jobPostingSearchCondition: JobPostingSearchCondition,
		siteUserId: Long,
		pageable: Pageable
	): Page<JobPostingPageResponse>{
		return jobPostingQueryRepository.findAllVoter(jobPostingSearchCondition, siteUserId, pageable);
	}
}
