package com.backend.domain.category.dto.request

import jakarta.validation.constraints.NotBlank

data class CategoryRequest(
    @field:NotBlank(message = "카테고리 이름을 입력해주세요.")
    val name: String = ""
)