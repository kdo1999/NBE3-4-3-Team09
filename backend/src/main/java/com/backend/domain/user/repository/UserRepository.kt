package com.backend.domain.user.repository

import com.backend.domain.user.entity.SiteUser
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<SiteUser, Long> {
    fun findByEmail(email: String?): SiteUser?
    fun findByKakaoId(kakaoId: String?): SiteUser?
}
