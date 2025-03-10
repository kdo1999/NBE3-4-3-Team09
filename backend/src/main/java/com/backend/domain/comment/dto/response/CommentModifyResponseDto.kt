package com.backend.domain.comment.dto.response

import com.backend.domain.comment.entity.Comment
import java.time.ZonedDateTime

data class CommentModifyResponseDto(
    val id: Long?,
    val content: String,
    val createdAt: ZonedDateTime,
    val modifiedAt: ZonedDateTime,
    val isAuthor: Boolean
) {
    companion object {
        fun fromEntity(comment: Comment, isAuthor: Boolean): CommentModifyResponseDto {
            return CommentModifyResponseDto(
                id = comment.id,
                content = comment.content,
                createdAt = comment.createdAt,
                modifiedAt = comment.modifiedAt,
                isAuthor = isAuthor
            )
        }
    }
}
