package com.backend.domain.comment.dto.request


import jakarta.validation.constraints.NotBlank

data class CommentRequestDto(
    @field:NotBlank(message = "댓글 내용을 입력하세요.")
    val content: String
)