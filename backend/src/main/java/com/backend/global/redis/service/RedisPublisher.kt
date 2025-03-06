package com.backend.global.redis.service

import com.backend.domain.chat.dto.response.ChatResponse
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import io.github.oshai.kotlinlogging.KotlinLogging

private val log = KotlinLogging.logger {}

@Service
class RedisPublisher(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun publish(postId: String, chatResponse: ChatResponse) {
        val channel = "postNum:$postId"
        log.info { "Publishing message to Redis: $channel" }

        // 메시지를 Redis Pub/Sub으로 전송
        redisTemplate.convertAndSend(channel, chatResponse)

        // Redis List에 채팅 메시지 저장 (채팅 로그 유지)
        redisTemplate.opsForList().leftPush("chatList:$postId", chatResponse)
        redisTemplate.expire("chatList:$postId", Duration.ofDays(1)) // 1일 후 자동 삭제
    }
}
