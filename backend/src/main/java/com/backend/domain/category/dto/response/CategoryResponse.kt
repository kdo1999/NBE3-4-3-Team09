package com.backend.domain.category.dto.response

import java.time.ZonedDateTime

data class CategoryResponse(
    val id: Long,
    val name: String,
    val createdAt: ZonedDateTime,
    val modifiedAt: ZonedDateTime
)
