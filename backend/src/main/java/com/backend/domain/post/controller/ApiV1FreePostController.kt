package com.backend.domain.post.controller

import com.backend.domain.post.dto.FreePostRequest
import com.backend.domain.post.dto.PostCreateResponse
import com.backend.domain.post.dto.PostResponse
import com.backend.domain.post.service.FreePostService
import com.backend.global.response.GenericResponse
import com.backend.global.security.custom.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/free/posts")
class ApiV1FreePostController(
    private val freePostService: FreePostService
) {

    @GetMapping("/{postId}")
    fun findById(
        @PathVariable postId: Long,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): GenericResponse<PostResponse> {
        val postResponse = freePostService.findById(postId, customUserDetails.getSiteUser())
        return GenericResponse.ok(postResponse)
    }

    @PostMapping
    fun save(
        @RequestBody @Valid freePostRequest: FreePostRequest,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): GenericResponse<PostCreateResponse> {
        val postCreateResponse = freePostService.save(freePostRequest, customUserDetails.getSiteUser())
        return GenericResponse.ok(HttpStatus.CREATED.value(), postCreateResponse)
    }

    @PatchMapping("/{postId}")
    fun update(
        @PathVariable postId: Long,
        @RequestBody @Valid freePostRequest: FreePostRequest,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): GenericResponse<PostResponse> {
        val postResponse = freePostService.update(postId, freePostRequest, customUserDetails.getSiteUser())
        return GenericResponse.ok(postResponse)
    }

    @DeleteMapping("/{postId}")
    fun delete(
        @PathVariable postId: Long,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): GenericResponse<Void> {
        freePostService.delete(postId, customUserDetails.getSiteUser())
        return GenericResponse.ok()
    }
}