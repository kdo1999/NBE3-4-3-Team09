package com.backend.domain.chat.converter

import com.backend.domain.chat.dto.request.ChatRequest
import com.backend.domain.chat.dto.response.ChatResponse
import com.backend.domain.chat.entity.Chat
import com.backend.domain.user.entity.SiteUser
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object ChatConverter {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    /**
     * ChatRequest → Chat 변환
     */
    fun toChat(chatRequest: ChatRequest, postId: String, user: SiteUser): Chat {
        val createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(formatter)

        return Chat(
            postId,
            chatRequest.userId,
            requireNotNull(user.name) { "Username cannot be null" },
            chatRequest.content,
            chatRequest.type,
            createdAt
        )
    }

    /**
     * Chat → ChatResponse 변환
     */
    fun toChatResponse(chat: Chat): ChatResponse {
        return ChatResponse(
            chat.id!!,
            chat.userId,
            chat.username,
            chat.content,
            chat.type,
            chat.createdAt
        )
    }
}
