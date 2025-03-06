package com.backend.domain.jobskill.repository;

import com.backend.domain.jobskill.entity.JobSkill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface JobSkillJpaRepository: JpaRepository<JobSkill, Long> {
	@Query("select js from JobSkill js where js.code = :code")
	fun findByCode(@Param("code") code: Int): Optional<JobSkill>

	fun findByName(name: String): Optional<JobSkill>
}
