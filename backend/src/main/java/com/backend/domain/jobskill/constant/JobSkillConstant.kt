package com.backend.domain.jobskill.constant

/**
 * JobSkillConstant
 *
 * JobSkill 관련 상수를 정의한 클래스 입니다.
 *
 * @author Kim Dong O
 */
enum class JobSkillConstant {
    //Redis에 저장될 JobSkill Key 값
    JOB_SKILL_REDIS_KEY("job_skill_key:");

    val key: String

    constructor(key: String) {
        this.key = key;
    }
}
