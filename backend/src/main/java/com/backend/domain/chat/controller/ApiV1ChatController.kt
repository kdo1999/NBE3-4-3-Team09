package com.backend.domain.chat.controller

import com.backend.domain.chat.dto.response.ChatResponses
import com.backend.domain.chat.service.ChatService
import com.backend.global.response.GenericResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

private val log = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/chat")
class ApiV1ChatController(
    private val chatService: ChatService
) {
    /**
     * 채팅 목록 조회 (Redis → 없으면 MongoDB)
     *
     * @param postId 채팅을 조회할 게시글 ID
     * @return `GenericResponse<ChatResponses>` 조회된 채팅 목록
     */
    @GetMapping("/list/{postId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getChattingList(@PathVariable postId: String): GenericResponse<ChatResponses> =
        chatService.getAllByPostId(postId)
            .also { log.info { "채팅 리스트 조회 완료 → postId = $postId" } }
            .let { GenericResponse.ok(it, "요청 성공") }
}
