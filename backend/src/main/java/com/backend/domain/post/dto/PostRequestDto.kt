package com.backend.domain.post.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.ZonedDateTime

data class PostRequestDto(

    @field:NotBlank(message = "제목을 입력해 주세요.")
    var subject: String,

    @field:NotBlank(message = "내용을 입력해 주세요.")
    var content: String,

    @field:NotNull(message = "카테고리를 선택해 주세요.")
    val categoryId: Long,

    val jobPosting: Long ?= null, // 모집 게시판 아닐 경우 null

    // 모집 게시판 전용 필드
    @field:Future(message = "모집 종료일은 미래 날짜여야 합니다.")
    var RecruitmentClosingDate: ZonedDateTime ?= null,

    @field:Min(value = 1, message = "모집 인원은 최소 1명이어야 합니다.")
    var numOfApplicants: Int? = null
)
