package com.backend.domain.comment.dto.response

import java.time.ZonedDateTime

data class CommentResponseDto(
    val id: Long,
    val content: String,
    val createdAt: ZonedDateTime,
    val modifiedAt: ZonedDateTime,
    val profileImageUrl: String,
    val authorName: String,
    var isAuthor: Boolean
)
