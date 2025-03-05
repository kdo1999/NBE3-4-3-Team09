package com.backend.domain.chat.dto.response

import com.backend.domain.chat.entity.MessageType

data class ChatResponse(
    val id: String,
    val userId: String,
    val username: String,
    val content: String,
    val type: MessageType,
    val createdAt: String
)
