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

)
