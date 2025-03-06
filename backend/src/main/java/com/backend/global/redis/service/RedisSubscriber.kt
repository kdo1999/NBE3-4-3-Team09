package com.backend.global.redis.service

import com.backend.domain.chat.dto.response.ChatResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import io.github.oshai.kotlinlogging.KotlinLogging

private val log = KotlinLogging.logger {}

@Service
class RedisSubscriber(
    private val messagingTemplate: SimpMessagingTemplate,
    private val objectMapper: ObjectMapper
) : MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val channel = String(message.channel)
        log.info { "Redis Subscriber - Received message from post: $channel" }

        val postId = channel.replace("postNum:", "")

        try {
            val chatResponse = objectMapper.readValue(message.body, ChatResponse::class.java)
            messagingTemplate.convertAndSend("/sub/$postId", chatResponse)
        } catch (e: Exception) {
            log.error(e) { "메세지 전송 실패" }
        }
    }
}
