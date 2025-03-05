package com.backend.domain.chat.dto.response

import com.backend.domain.chat.entity.MessageTypeKt

data class ChatResponseKt(
    val id: String,
    val userId: String,
    val username: String,
    val content: String,
    val type: MessageTypeKt,
    val createdAt: String
)
