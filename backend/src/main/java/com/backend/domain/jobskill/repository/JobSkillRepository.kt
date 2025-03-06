package com.backend.domain.jobskill.repository;

import com.backend.domain.jobskill.entity.JobSkill
import java.util.*

/**
 * JobSkillRepository
 * <p>JobSkill 리포지토리 입니다.</p>
 *
 * @author Kim Dong O
 */
interface JobSkillRepository {

	/**
	 * @param id JobSkill id
	 * @return {@link Optional<JobSkill>}
	 * @implSpec Id 값으로 조회 메서드 입니다.
	 */
	fun findById(id: Long): Optional<JobSkill>

	/**
	 * @param code JobSkill code
	 * @return {@link Optional<JobSkill>}
	 * @implSpec code 값으로 조회 메서드 입니다.
	 */
	fun findByCode(code: Int): Optional<JobSkill>

	/**
	 * @param jobSkill JobSkill 객체
	 * @return {@link JobSkill}
	 * @implSpec JobSkill 저장 메서드 입니다.
	 */
	fun save(jobSkill: JobSkill): JobSkill

	/**
	 * @param name JobSkill name
	 * @return {@link Optional<JobSkill>}
	 * @implSpec name 값으로 조회 메서드 입니다.
	 */
	fun findByName(name: String): Optional<JobSkill>

    fun saveAll(newJobSkill: List<JobSkill>)
}
