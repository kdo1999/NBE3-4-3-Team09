package com.backend.domain.user.controller

import com.backend.domain.user.dto.request.UserModifyProfileRequest
import com.backend.domain.user.dto.response.UserGetProfileResponse
import com.backend.domain.user.service.UserService
import com.backend.global.response.GenericResponse
import com.backend.global.security.custom.CustomUserDetails
import lombok.RequiredArgsConstructor
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class ApiV1UserController(
    private val userService: UserService
) {
    /**
     * 유저 정보를 출력하기 위한 메서드 입니다.
     *
     * @param userId 유저 고유 식별 id
     * @param customUserDetails
     * @return [GenericResponse]
     */
    @GetMapping("/users/{user_id}")
    fun getProfile(
        @PathVariable(name = "user_id") userId: Long,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): GenericResponse<UserGetProfileResponse> {
        val userGetProfileResponse = userService.getUser(userId, customUserDetails)?.let{UserGetProfileResponse(it)}
        return GenericResponse.ok(userGetProfileResponse)
    }

    /**
     * 유저 정보를 수정하기 위한 메서드 입니다.
     *
     * @param userId 유저 고유 식별 id
     * @param req 유저 프로필 수정 DTO
     * @param customUserDetails
     * @return [GenericResponse]
     */
    @PatchMapping("/users/{user_id}")
    fun modifyProfile(
        @PathVariable(name = "user_id") userId: Long,
        @RequestBody req: UserModifyProfileRequest,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): GenericResponse<Void> {
        userService.modifyUser(userId, customUserDetails, req)

        return GenericResponse.ok()
    }
}
