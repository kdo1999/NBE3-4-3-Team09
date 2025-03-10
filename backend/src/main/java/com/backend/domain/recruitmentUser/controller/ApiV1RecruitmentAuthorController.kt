package com.backend.domain.recruitmentUser.controller

import com.backend.domain.recruitmentUser.dto.request.AuthorRequest
import com.backend.domain.recruitmentUser.dto.response.RecruitmentUserPageResponse
import com.backend.domain.recruitmentUser.service.RecruitmentAuthorService
import com.backend.global.response.GenericResponse
import com.backend.global.security.custom.CustomUserDetails
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/v1/recruitment")
class ApiV1RecruitmentAuthorController(
    private val recruitmentAuthorService: RecruitmentAuthorService
) {

    /**
     * 모집 지원 승인: 작성자가 특정 지원자의 모집 신청을 승인합니다.
     */
    @PatchMapping("/{postId}/accept")
    fun approveRecruitment(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @PathVariable postId: Long,
        @RequestBody @Valid request: AuthorRequest
    ): GenericResponse<Unit> {
        recruitmentAuthorService.recruitmentAccept(userDetails.getSiteUser(), postId, request.userId)
        return GenericResponse.ok()
    }

    /**
     * 모집 지원 거절: 작성자가 특정 지원자의 모집 신청을 거절합니다.
     */
    @PatchMapping("/{postId}/reject")
    fun rejectRecruitment(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @PathVariable postId: Long,
        @RequestBody @Valid request: AuthorRequest
    ): GenericResponse<Unit> {
        recruitmentAuthorService.recruitmentReject(userDetails.getSiteUser(), postId, request.userId)
        return GenericResponse.ok()
    }

    /**
     * 모집 지원자 목록 조회: 작성자가 본인의 모집 게시글에 지원한 사용자 목록을 조회합니다.
     */
    @GetMapping("/{postId}/applied-users")
    fun getAppliedUsers(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @PathVariable postId: Long,
        @RequestParam(name = "pageNum", defaultValue = "0") pageNum: Int,
        @RequestParam(name = "pageSize", defaultValue = "10") pageSize: Int
    ): GenericResponse<RecruitmentUserPageResponse> {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"))

        val appliedUsers = recruitmentAuthorService.getAppliedUserList(userDetails.getSiteUser(), postId, pageable)
        return GenericResponse.ok(appliedUsers)
    }

    /**
     * 모집 승인된 참여자 목록 조회: 모집이 완료된 후 승인된 지원자 목록을 조회합니다.
     */
    @GetMapping("/{postId}/accepted-users")
    fun getAcceptedUsers(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @PathVariable postId: Long,
        @RequestParam(name = "pageNum", defaultValue = "0") pageNum: Int,
        @RequestParam(name = "pageSize", defaultValue = "10") pageSize: Int
    ): GenericResponse<RecruitmentUserPageResponse> {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"))

        val acceptedUsers = recruitmentAuthorService.getAcceptedUserList(userDetails.getSiteUser(), postId, pageable)
        return GenericResponse.ok(acceptedUsers)
    }
}
