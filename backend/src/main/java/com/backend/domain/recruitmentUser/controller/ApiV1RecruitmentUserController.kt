package com.backend.domain.recruitmentUser.controller

import com.backend.domain.recruitmentUser.dto.response.RecruitmentUserPostResponse
import com.backend.domain.recruitmentUser.service.RecruitmentUserService
import com.backend.global.response.GenericResponse
import com.backend.global.security.custom.CustomUserDetails
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * 모집 신청 및 조회를 담당하는 컨트롤러 요청 경로: /api/v1/recruitment-user
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recruitment")
class ApiV1RecruitmentUserController(
    private val recruitmentUserService: RecruitmentUserService
) {

    /**
     * 모집 신청 사용자가 특정 게시글에 모집을 신청합니다.
     *
     * @param userDetails 현재 로그인한 사용자
     * @param postId      모집할 게시글 ID (URL Path)
     * @return 성공 응답 (201 Created)
     */
    @PostMapping("/{postId}")
    fun applyRecruitment(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @PathVariable("postId") postId: Long): GenericResponse<Void> {

        recruitmentUserService.saveRecruitment(
                userDetails.getSiteUser(),
                postId
        )
        return GenericResponse.ok(HttpStatus.CREATED.value())
    }

    /**
     * 모집 신청 취소 사용자가 본인의 모집 신청을 취소합니다.
     *
     * @param userDetails 현재 로그인된 사용자
     * @param postId      모집 취소할 게시글 ID (URL Path)
     * @return 성공 응답 (200 OK)
     */
    @DeleteMapping("/{postId}")
    fun cancelRecruitment(
            @AuthenticationPrincipal userDetails: CustomUserDetails,
            @PathVariable("postId") postId: Long): GenericResponse<Void> {

        recruitmentUserService.cancelRecruitment(
                userDetails.getSiteUser(),
                postId
        )

        return GenericResponse.ok()
    }

    /**
     * 모집 승인된 게시글 조회 사용자가 특정 모집 상태(기본값: ACCEPTED)인 게시글 목록을 페이징하여 조회합니다.
     *
     * @param userDetails 현재 로그인된 사용자
     * @param status      모집 상태 (기본값: "ACCEPTED")
     * @param pageNum     페이지 번호 (기본값: 0)
     * @param pageSize    페이지 크기 (기본값: 10)
     * @return 모집 승인된 게시글 목록 (Page<PostResponseDto>)
     */
    @GetMapping("/accepted-posts")
    fun getAcceptedPosts(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @RequestParam(defaultValue = "ACCEPTED", name = "status") status: String,
        @RequestParam(defaultValue = "0", name = "pageNum") pageNum: Int,
        @RequestParam(defaultValue = "10", name = "pageSize") pageSize: Int
    ): GenericResponse<RecruitmentUserPostResponse> {

        val pageable: Pageable = PageRequest.of(pageNum, pageSize,
                Sort.by(Sort.Direction.ASC, "createdAt"))

        val acceptedPosts = recruitmentUserService.getAcceptedPosts(
                userDetails.getSiteUser(),
                status,
                pageable
        )

        return GenericResponse.ok(acceptedPosts)
    }
}
