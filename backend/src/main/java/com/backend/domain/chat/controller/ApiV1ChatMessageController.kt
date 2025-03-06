package com.backend.domain.chat.controller

import com.backend.domain.chat.dto.request.ChatRequest
import com.backend.domain.chat.service.ChatService
import com.backend.global.redis.service.RedisPublisher
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.messaging.handler.annotation.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

private val log = KotlinLogging.logger {}

@Controller
@RequestMapping("/api/v1/chat")
class ApiV1ChatMessageController(
    private val chatService: ChatService,
    private val redisPublisher: RedisPublisher
) {

    /**
     * 홈 경로 반환 (테스트용)
     *
     * @return 테스트 페이지
     */
    @GetMapping
    fun home(): String = "mongo"

    /**
     * STOMP WebSocket을 통해 메시지 처리
     * - `/pub/chat/{postId}` 경로로 클라이언트가 메시지 전송
     * - Redis Pub/Sub을 통해 다른 구독자들에게 전파
     *
     * @param postId 게시글 ID
     * @param chatRequest 클라이언트가 보낸 채팅 메시지 요청
     */
    @MessageMapping("/chat/{postId}")
    @SendTo("/sub/{postId}")
    fun sendMessage(
        @DestinationVariable postId: String,
        @Payload chatRequest: ChatRequest
    ) {
        val chatResponse = chatService.save(chatRequest, postId)
            .also { log.info { "채팅 저장 완료 → postId: $postId, username: ${it.username}" } }

        redisPublisher.publish("postNum:$postId", chatResponse)
            .also { log.info { "메시지 Redis 전송 완료 → 채널: postNum:$postId" } }
    }
}
