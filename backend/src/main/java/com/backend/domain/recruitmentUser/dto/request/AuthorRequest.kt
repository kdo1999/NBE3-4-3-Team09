package com.backend.domain.recruitmentUser.dto.request

import jakarta.validation.constraints.NotNull

data class AuthorRequest(
        @field:NotNull(message = "모집 인원 Id는 필수입니다.")
        val userId: Long
) {

}
