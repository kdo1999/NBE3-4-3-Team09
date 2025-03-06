package com.backend.domain.jobskill.repository;

import com.backend.domain.jobskill.entity.JobSkill
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JobSkillRepositoryImpl(private val jobSkillJpaRepository: JobSkillJpaRepository)
	: JobSkillRepository {

	override fun findById(id: Long): Optional<JobSkill> {
		return jobSkillJpaRepository.findById(id)
	}

	override fun findByCode(code: Int): Optional<JobSkill> {
		return jobSkillJpaRepository.findByCode(code)
	}

	override fun save(jobSkill: JobSkill): JobSkill {
		return jobSkillJpaRepository.save(jobSkill)
	}

	override fun findByName(name: String): Optional<JobSkill> {
		return jobSkillJpaRepository.findByName(name)
	}

	@Override
	override fun saveAll(newJobSkill: List<JobSkill>) {
		jobSkillJpaRepository.saveAll(newJobSkill)
	}
}
