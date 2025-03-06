package com.backend.domain.user.repository

import com.backend.domain.user.entity.SiteUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<SiteUser, Long> {
    fun findByEmail(email: String?): Optional<SiteUser>?
    fun findByKakaoId(kakaoId: String?): Optional<SiteUser>?
}
