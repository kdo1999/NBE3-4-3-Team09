package com.backend.domain.post.controller

import com.backend.domain.post.dto.*
import com.backend.domain.post.service.RecruitmentPostService
import com.backend.global.response.GenericResponse
import com.backend.global.security.custom.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/recruitment/posts")
class ApiV1RecruitmentPostController(
    private val recruitmentPostService: RecruitmentPostService
) {

    @GetMapping("/{postId}")
    fun findById(
        @PathVariable postId: Long,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): GenericResponse<RecruitmentPostResponse> {
        val postResponse = recruitmentPostService.findById(postId, customUserDetails.getSiteUser())
        return GenericResponse.ok(postResponse)
    }

    // 모집 게시글 생성
    @PostMapping
    fun createPost(
        @RequestBody @Valid recruitmentPostRequest: RecruitmentPostRequest,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): GenericResponse<PostCreateResponse> {
        val postCreateResponse = recruitmentPostService.save(recruitmentPostRequest, customUserDetails.getSiteUser())
        return GenericResponse.ok(HttpStatus.CREATED.value(), postCreateResponse)
    }

    // 모집 게시글 수정
    @PatchMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody @Valid requestDto: RecruitmentPostRequest,
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): GenericResponse<RecruitmentPostResponse> {
        val result = recruitmentPostService.update(postId, requestDto, userDetails.getSiteUser())
        return GenericResponse.ok(result)
    }

    // 모집 게시글 삭제
    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): GenericResponse<Void> {
        recruitmentPostService.delete(postId, customUserDetails.getSiteUser())
        return GenericResponse.ok()
    }
}