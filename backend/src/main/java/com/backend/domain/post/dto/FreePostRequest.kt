package com.backend.domain.post.dto

import jakarta.validation.constraints.NotBlank

open class FreePostRequest(

    @field:NotBlank(message = "제목을 입력해 주세요")
    open var subject: String,

    @field:NotBlank(message = "내용을 입력해 주세요")
    open var content: String

)
