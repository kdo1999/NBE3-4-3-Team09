package com.backend.domain.chat.converter

import com.backend.domain.chat.dto.request.ChatRequestKt
import com.backend.domain.chat.dto.response.ChatResponseKt
import com.backend.domain.chat.entity.ChatKt
import com.backend.domain.user.entity.SiteUser
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object ChatConverterKt {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    /**
     * ChatRequest → Chat 변환
     */
    fun toChat(chatRequest: ChatRequestKt, postId: String, user: SiteUser): ChatKt {
        val createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(formatter)

        return ChatKt(
            postId,
            chatRequest.userId,
            "username", // TODO SiteUser 작업후 적용
            chatRequest.content,
            chatRequest.type,
            createdAt
        )
    }

    /**
     * Chat → ChatResponse 변환
     */
    fun toChatResponse(chat: ChatKt): ChatResponseKt {
        return ChatResponseKt(
            chat.id!!,
            chat.userId,
            chat.username,
            chat.content,
            chat.type,
            chat.createdAt
        )
    }
}
