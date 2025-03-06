package com.backend.domain.jobskill.dto;

import com.querydsl.core.annotations.QueryProjection

/**
 * JobSkillResponse
 * <p>JobSkill 응답용 객체 입니다.</p>
 *
 * @param name DisplayName
 * @param code JobSkillCode
 * @author Kim Dong O
 */
data class JobSkillResponse @QueryProjection constructor(
	val name: String,
	val code: Int
)