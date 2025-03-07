package com.backend.global.security.custom

import com.backend.domain.user.entity.SiteUser
import com.backend.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String?): UserDetails {
        val siteUser = Optional.ofNullable<SiteUser?>(userRepository.findByEmail(email))
            .orElseThrow { UsernameNotFoundException("사용자를 찾을 수 없습니다.") }

        return CustomUserDetails(siteUser)
    }
}
