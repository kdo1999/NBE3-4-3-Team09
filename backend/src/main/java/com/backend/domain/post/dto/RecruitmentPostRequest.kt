package com.backend.domain.post.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.ZonedDateTime

data class RecruitmentPostRequest(

    override var subject: String,
    override var content: String,

    @field:NotNull
    val jobPostingId: Long,

    @field:Min(value = 1, message = "모집 인원은 최소 1명 이상이어야 합니다.")
    var numOfApplicants: Int,

    @field:Future(message = "모집 종료 일은 오늘 이후여야 한니다.")
    var recruitmentClosingDate: ZonedDateTime
) : FreePostRequest(subject, content)

