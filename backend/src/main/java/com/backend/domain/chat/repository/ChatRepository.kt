package com.backend.domain.chat.repository.kotlin

import com.backend.domain.chat.entity.ChatKt
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatRepository : MongoRepository<ChatKt, String>, CustomChatRepository
