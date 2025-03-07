package com.backend.global.security

import com.backend.global.redis.repository.RedisRepository
import com.backend.global.response.GenericResponse
import com.backend.global.security.filter.JwtAuthenticationFilter
import com.backend.global.security.filter.JwtAuthorizationFilter
import com.backend.global.security.handler.JwtLogoutHandler
import com.backend.global.security.handler.JwtLogoutSuccessHandler
import com.backend.global.security.handler.OAuth2LoginFailureHandler
import com.backend.global.security.handler.OAuth2LoginSuccessHandler
import com.backend.global.security.oauth.CustomOAuth2UserService
import com.backend.standard.util.AuthResponseUtil
import com.backend.standard.util.JwtUtil
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
open class SecurityConfig(
    private val jwtUtil: JwtUtil,
    private val objectMapper: ObjectMapper,
    private val redisRepository: RedisRepository,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler,
    private val oAuth2LoginFailureHandler: OAuth2LoginFailureHandler
) {

    @Value("\${jwt.token.access-expiration}")
    private val accessExpiration: Long = 0

    @Value("\${jwt.token.refresh-expiration}")
    private val refreshExpiration: Long = 0

    @Bean
    open fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    open fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    @Bean
    @Throws(Exception::class)
    open fun securityFilterChain(http: HttpSecurity, configuration: AuthenticationConfiguration): SecurityFilterChain {
        val jwtAuthenticationFilter = JwtAuthenticationFilter(
            jwtUtil, accessExpiration, refreshExpiration, objectMapper, redisRepository,
            authenticationManager(configuration)
        ).apply {
            setFilterProcessesUrl("/api/v1/adm/login")
        }

        val jwtAuthorizationFilter = JwtAuthorizationFilter(
            jwtUtil, accessExpiration, refreshExpiration, objectMapper, redisRepository
        )

        http
            .headers { headers -> headers.frameOptions { it.sameOrigin() } }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authorize ->
                PUBLIC_URLS.forEach { (method, patterns) ->
                    patterns.forEach { pattern ->
                        authorize.requestMatchers(method, pattern).permitAll()
                    }
                }
                authorize
                    .requestMatchers("/h2-console/**", "/ws/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/adm/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/category").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/category/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/v1/category/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/category/**").hasRole("ADMIN")
                    .requestMatchers(
                        HttpMethod.GET,
                        "/api/v1/job-posting/{id}", "/api/v1/job-posting/voter",
                        "/api/v1/free/posts/{postId}", "/api/v1/recruitment/posts/{postId}",
                        "/api/v1/posts/{postId}/comments"
                    ).hasAnyRole("USER", "ADMIN")
                    .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/v1/recruitment/**", "/api/v1/voter/*",
                        "/api/v1/free/posts/{postId}", "/api/v1/recruitment/posts/{postId}",
                        "/api/v1/posts/{postId}/comments/{id}"
                    ).hasAnyRole("USER", "ADMIN")
                    .requestMatchers(
                        HttpMethod.PATCH,
                        "/api/v1/recruitment/**", "/api/v1/free/posts/{postId}",
                        "/api/v1/recruitment/posts/{postId}", "/api/v1/posts/{postId}/comments/{id}"
                    ).hasAnyRole("USER", "ADMIN")
                    .requestMatchers(
                        HttpMethod.POST,
                        "/api/v1/voter", "/api/v1/recruitment/**",
                        "/api/v1/free/posts", "/api/v1/recruitment/posts", "/api/v1/posts/{postId}/comments"
                    ).hasAnyRole("USER", "ADMIN")
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { exceptions ->
                exceptions.authenticationEntryPoint { _, response, _ ->
                    AuthResponseUtil.failLogin(
                        response,
                        GenericResponse.fail<Any>(HttpStatus.UNAUTHORIZED.value()),
                        HttpServletResponse.SC_UNAUTHORIZED,
                        objectMapper
                    )
                }
                exceptions.accessDeniedHandler { _, response, _ ->
                    AuthResponseUtil.failLogin(
                        response,
                        GenericResponse.fail<Any>(HttpStatus.FORBIDDEN.value()),
                        HttpServletResponse.SC_FORBIDDEN,
                        objectMapper
                    )
                }
            }
            .logout { logout ->
                logout
                    .logoutUrl("/api/v1/logout")
                    .addLogoutHandler(JwtLogoutHandler(jwtUtil, redisRepository))
                    .logoutSuccessHandler(JwtLogoutSuccessHandler(objectMapper))
            }
            .oauth2Login { oauth2 ->
                oauth2.userInfoEndpoint { userInfo ->
                    userInfo.userService(customOAuth2UserService)
                }
                    .successHandler(oAuth2LoginSuccessHandler)
                    .failureHandler(oAuth2LoginFailureHandler)
            }

        return http.build()
    }

    @Bean
    open fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
            allowedOrigins = listOf("http://localhost:3000", "http://localhost:8080")
            allowCredentials = true
            allowedHeaders = listOf("*")
            exposedHeaders = listOf("Authorization", "Set-Cookie")
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }

    companion object {
        private val PUBLIC_URLS: Map<HttpMethod, List<String>> = mapOf(
            HttpMethod.GET to listOf(
                "/api/v1/job-posting",
                "/h2-console/**",
                "/login/oauth2/code/kakao",
                "/oauth2/authorization/kakao",
                "/api/v1/chat/**",
                "/api/v1/recruitment",
                "/api/v1/posts",
                "/api/v1/category",
                "/ws/**",
                "/api/v1/adm/login"
            ),
            HttpMethod.POST to listOf("/api/v1/adm/login")
        )

        @JvmStatic
        fun getPublicUrls(): Map<HttpMethod, List<String>> = PUBLIC_URLS
    }
}