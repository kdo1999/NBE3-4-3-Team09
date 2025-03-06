package com.backend.domain.chat.repository

import com.backend.domain.chat.entity.Chat

interface CustomChatRepository {
    fun findChatsByPost(postId: String): List<Chat>
    fun findRecentChatsByPost(postId: String, limit: Int): List<Chat>
    fun findChatsBefore(postId: String, lastMessageId: String, limit: Int): List<Chat>
}
