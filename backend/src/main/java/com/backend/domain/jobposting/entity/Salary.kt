package com.backend.domain.jobposting.entity;

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * Salary
 * <p>연봉 조건을 관리하는 객체 입니다.</p>
 *
 * @author Kim Dong O
 */
@Embeddable
class Salary {

	@Column(name = "salary_code", nullable = false)
	var code: Int? = null //code

	@Column(name = "salary_name", length = 20)
	lateinit var name: String //DisplayName

	constructor(code: Int, name: String) {
		this.code = code
		this.name = name
	}
}
