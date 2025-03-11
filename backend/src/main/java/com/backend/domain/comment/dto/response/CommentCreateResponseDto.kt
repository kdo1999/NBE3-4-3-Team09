package com.backend.domain.comment.dto.response

import com.backend.domain.comment.entity.Comment
import java.time.ZonedDateTime

data class CommentCreateResponseDto(
    val id: Long,
    val content: String,
    val createdAt: ZonedDateTime,
    val modifiedAt: ZonedDateTime
) {
    companion object {
        fun fromEntity(comment: Comment): CommentCreateResponseDto {
            return CommentCreateResponseDto(
                id = comment.id!!,
                content = comment.content,
                createdAt = comment.createdAt,
                modifiedAt = comment.modifiedAt
            )
        }
    }
}