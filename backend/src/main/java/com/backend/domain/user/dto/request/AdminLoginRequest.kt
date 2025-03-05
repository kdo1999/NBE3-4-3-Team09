package com.backend.domain.user.dto.request

import com.backend.domain.user.entity.SiteUser
import jakarta.validation.constraints.NotBlank

data class AdminLoginRequest(
    @field:NotBlank
    val email: String?,
    
    @field:NotBlank
    val password: String?
) {
    constructor(siteUser: SiteUser) : this(
        email = siteUser.email,
        password = siteUser.password
    )
}
