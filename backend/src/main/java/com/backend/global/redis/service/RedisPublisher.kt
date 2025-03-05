package com.backend.global.redis.service

import com.backend.domain.chat.dto.response.ChatResponseKt
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration


@Service
class RedisPublisherKt(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun publish(postId: String, chatResponse: ChatResponseKt) {
        val channel = "postNum:$postId"

        // 메시지를 Redis Pub/Sub으로 전송
        redisTemplate.convertAndSend(channel, chatResponse)

        // Redis List에 채팅 메시지 저장 (채팅 로그 유지)
        redisTemplate.opsForList().leftPush("chatList:$postId", chatResponse)
        redisTemplate.expire("chatList:$postId", Duration.ofDays(1)) // 1일 후 자동 삭제
    }
}
