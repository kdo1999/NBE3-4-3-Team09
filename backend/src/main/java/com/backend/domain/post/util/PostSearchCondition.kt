package com.backend.domain.post.util

import jakarta.validation.constraints.Min

data class PostSearchCondition(
    val categoryId: Long?,
    val kw: String?,
    val sort: String?,
    val order: String?,
    @field:Min(value = 0, message = "음수는 입력할 수 없습니다.")
    val pageNum: Int?,
    @field:Min(value = 1, message = "1 이상의 값을 입력해 주세요.")
    val pageSize: Int?
)
