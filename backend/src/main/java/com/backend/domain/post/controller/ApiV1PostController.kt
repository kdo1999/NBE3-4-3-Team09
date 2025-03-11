package com.backend.domain.post.controller

import com.backend.domain.post.dto.PostPageResponse
import com.backend.domain.post.service.PostService
import com.backend.domain.post.util.PostSearchCondition
import com.backend.global.response.GenericResponse
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class ApiV1PostController(
    private val postService: PostService
) {
    @GetMapping
fun findAll(@Valid postSearchCondition: PostSearchCondition): GenericResponse<Page<PostPageResponse>> {
    val postPageResponsePage = postService.findAll(postSearchCondition)
    return GenericResponse.ok(postPageResponsePage)
    }
}