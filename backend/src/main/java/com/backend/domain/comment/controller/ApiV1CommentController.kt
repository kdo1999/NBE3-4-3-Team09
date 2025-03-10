package com.backend.domain.comment.controller

import com.backend.domain.comment.dto.request.CommentRequestDto
import com.backend.domain.comment.dto.response.CommentCreateResponseDto
import com.backend.domain.comment.dto.response.CommentModifyResponseDto
import com.backend.domain.comment.dto.response.CommentResponseDto
import com.backend.domain.comment.service.CommentService
import com.backend.global.response.GenericResponse
import com.backend.global.security.custom.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
class ApiV1CommentController(
    private val commentService: CommentService
) {

    @GetMapping
    fun getComments(
        @PathVariable("postId") postId: Long,
        @RequestParam(defaultValue = "0", name = "page") page: Int,
        @RequestParam(defaultValue = "10", name = "size") size: Int,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): GenericResponse<Page<CommentResponseDto>> {
        val commentPage = commentService.getComments(postId, page, size, customUserDetails.getSiteUser())
        return GenericResponse.ok(commentPage, "댓글을 조회합니다.")
    }

    @PostMapping
    fun createComment(
        @PathVariable("postId") postId: Long,
        @Valid @RequestBody requestDto: CommentRequestDto,
        @AuthenticationPrincipal user: CustomUserDetails
    ): GenericResponse<CommentCreateResponseDto> {
        val commentResponseDto = commentService.createComment(requestDto, postId, user)
        return GenericResponse.ok(HttpStatus.CREATED.value(), commentResponseDto, "댓글이 정상적으로 생성되었습니다.")
    }

    @PatchMapping("/{id}")
    fun modifyComment(
        @PathVariable("postId") postId: Long,
        @PathVariable("id") commentId: Long,
        @RequestBody commentRequestDto: CommentRequestDto,
        @AuthenticationPrincipal user: CustomUserDetails
    ): GenericResponse<CommentModifyResponseDto> {
        val commentModifyResponseDto = commentService.modifyComment(postId, commentId, commentRequestDto, user)
        return GenericResponse.ok(commentModifyResponseDto, "댓글 수정에 성공하였습니다.")
    }

    @DeleteMapping("/{id}")
    fun deleteComment(
        @PathVariable("id") id: Long,
        @AuthenticationPrincipal user: CustomUserDetails
    ): GenericResponse<Void> {
        commentService.deleteComment(id, user.getSiteUser())
        return GenericResponse.ok("댓글이 삭제되었습니다.")
    }
}