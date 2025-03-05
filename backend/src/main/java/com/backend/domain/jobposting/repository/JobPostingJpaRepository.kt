package com.backend.domain.jobposting.repository;

import com.backend.domain.jobposting.entity.JobPosting
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface JobPostingJpaRepository: JpaRepository<JobPosting, Long> {
	@Query("select j from JobPosting j left join fetch j._jobPostingJobSkillList where j.id = :id")
	override fun findById(@Param("id") id: Long): Optional<JobPosting>

	@Query("SELECT j.id FROM JobPosting j")
	fun findIdsAll(): List<Long>
}
