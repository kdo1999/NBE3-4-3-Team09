package com.backend.domain.jobposting.repository;

import com.backend.domain.jobposting.entity.JobPosting
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JobPostingJpaRepository: JpaRepository<JobPosting, Long> {
	@Query("select j from JobPosting j left join fetch j._jobPostingJobSkillList where j.id = :id")
	fun findByIdOrNull(@Param("id") id: Long): JobPosting?

	@Query("SELECT j.id FROM JobPosting j")
	fun findIdsAll(): List<Long>
}
