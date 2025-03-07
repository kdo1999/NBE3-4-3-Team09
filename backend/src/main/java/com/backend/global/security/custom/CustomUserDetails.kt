package com.backend.global.security.custom

import com.backend.domain.user.entity.SiteUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val siteUser: SiteUser
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority?> {
        val authorities: MutableCollection<GrantedAuthority?> = ArrayList<GrantedAuthority?>()

        // SiteUser에서 역할 뽑아 SimpleGranted 변환
        authorities.add(SimpleGrantedAuthority(siteUser.userRole))

        return authorities
    }

    fun getSiteUser(): SiteUser {
        return siteUser
    }

    val id: Long?
        get() = siteUser.id

    val introduction: String?
        get() = siteUser.introduction

    val job: String?
        get() = siteUser.job

    override fun getUsername(): String? {
        return siteUser.email
    }

    override fun getPassword(): String? {
        return siteUser.password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
