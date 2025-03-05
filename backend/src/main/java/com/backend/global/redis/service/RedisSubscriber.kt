package com.backend.global.redis.service

import com.backend.domain.chat.dto.response.ChatResponse
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class RedisSubscriberKt(
    private val messagingTemplate: SimpMessagingTemplate,
    private val objectMapper: ObjectMapper
) : MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val channel = message.channel.toString(Charsets.UTF_8)

        val postId = channel.removePrefix("postNum:")

        try {
            val chatResponse = objectMapper.readValue(message.body, ChatResponse::class.java)
            messagingTemplate.convertAndSend("/sub/$postId", chatResponse)
        } catch (e: Exception) {
            log.error(e) { "메시지 변환 또는 전송 실패" }
        }
    }
}
