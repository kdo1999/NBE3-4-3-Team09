package com.backend.domain.chat.repository.kotlin

import com.backend.domain.chat.entity.ChatKt

interface CustomChatRepository {
    fun findChatsByPost(postId: String): List<ChatKt>
    fun findRecentChatsByPost(postId: String, limit: Int): List<ChatKt>
    fun findChatsBefore(postId: String, lastMessageId: String, limit: Int): List<ChatKt>
}
