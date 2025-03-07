package com.backend.domain.post.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.ZonedDateTime

data class PostPageResponse @QueryProjection constructor(
    val postId: Long,
    var subject: String,
    val categoryName: String,
    val authorName: String,
    val authorProfileImage: String?,
    var commentCount: Long,
    var voterCounter: Long,
    val createdAt: ZonedDateTime
)