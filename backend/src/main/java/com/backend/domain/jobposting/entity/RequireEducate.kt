package com.backend.domain.jobposting.entity;

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * RequireEducate
 * <p>지원자 학력 조건을 관리하는 객체 입니다.</p>
 *
 * @author Kim Dong O
 */
@Embeddable
public class RequireEducate {

    @Column(name = "require_educate_code")
    var code: Int? = null //code
        protected set

    @Column(name = "require_educate_name")
    lateinit var name: String //DisplayName
        protected set

    constructor(code: Int, name: String) {
        this.code = code
        this.name = name
    }
}
