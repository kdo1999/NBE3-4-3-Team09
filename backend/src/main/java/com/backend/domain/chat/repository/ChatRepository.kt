package com.backend.domain.chat.repository

import com.backend.domain.chat.entity.Chat
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatRepository : MongoRepository<Chat, String>, CustomChatRepository
