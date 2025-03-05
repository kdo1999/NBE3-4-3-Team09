package com.backend.domain.jobposting.entity;

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * ExperienceLevel
 * <p>지원자 경력 조건을 관리하는 객체 입니다.</p>
 *
 * @author Kim Dong O
 */
@Embeddable
class ExperienceLevel {

	@Column(name = "experience_level_code")
	var code: Int? = null //code

	@Column(name = "experience_level_min")
	var min: Int? = null //경력 최소 값

	@Column(name = "experience_level_max")
	var max: Int? = null //경력 최대 값

	@Column(name = "experience_level_name")
	lateinit var name: String //DisplayName
		protected set

	constructor(code: Int, min: Int, max: Int, name: String) {
		this.code = code
		this.min = min
		this.max = max
		this.name = name
	}
}
