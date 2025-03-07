package com.backend.domain.recruitmentUser.dto.request

import jakarta.validation.constraints.Min

data class AuthorRequest(
        @field:Min(1, message = "모집 인원 Id는 필수입니다.")
        val userId: Long
) {

}
