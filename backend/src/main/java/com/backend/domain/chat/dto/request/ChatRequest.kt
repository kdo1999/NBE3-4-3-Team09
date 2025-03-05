package com.backend.domain.chat.dto.request


import com.backend.domain.chat.entity.MessageTypeKt
import jakarta.validation.constraints.NotBlank

data class ChatRequestKt(
    val type: MessageTypeKt,

    @field:NotBlank
    val userId: String,

    @field:NotBlank
    val content: String
)
