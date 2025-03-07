package com.backend.domain.post.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.ZonedDateTime

data class PostResponse @QueryProjection constructor(
    val id: Long,
    var subject: String,
    var content: String,
    val categoryId: Long,
    val isAuthor: Boolean,
    val authorName: String,
    val authorImg: String,
    var voterCount: Long,
    var isVoter: Boolean,
    val createdAt: ZonedDateTime
)
