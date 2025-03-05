package com.backend.domain.chat.dto.request


import com.backend.domain.chat.entity.MessageType
import jakarta.validation.constraints.NotBlank

data class ChatRequest(
    val type: MessageType,

    @field:NotBlank
    val userId: String,

    @field:NotBlank
    val content: String
)
