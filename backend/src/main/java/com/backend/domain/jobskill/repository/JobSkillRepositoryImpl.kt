package com.backend.domain.jobskill.repository;

import com.backend.domain.jobskill.entity.JobSkill
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class JobSkillRepositoryImpl(private val jobSkillJpaRepository: JobSkillJpaRepository)
	: JobSkillRepository {

	override fun findById(id: Long): JobSkill? {
		return jobSkillJpaRepository.findByIdOrNull(id)
	}

	override fun findByCode(code: Int): JobSkill? {
		return jobSkillJpaRepository.findByCode(code)
	}

	override fun save(jobSkill: JobSkill): JobSkill {
		return jobSkillJpaRepository.save(jobSkill)
	}

	override fun findByName(name: String): JobSkill? {
		return jobSkillJpaRepository.findByNameOrNull(name)
	}

	@Override
	override fun saveAll(newJobSkill: List<JobSkill>) {
		jobSkillJpaRepository.saveAll(newJobSkill)
	}
}
