package com.backend.domain.chat.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "chat")
@CompoundIndex(def = "{'postId': 1, '_id': -1}")
data class ChatKt(
    @Id val id: String? = null,

    val postId: String,
    val userId: String,
    val username: String,
    val content: String,
    val type: MessageTypeKt,
    val createdAt: String
) {
    constructor(
        postId: String,
        userId: String,
        username: String,
        content: String,
        type: MessageTypeKt,
        createdAt: String
    ) : this(null, postId, userId, username, content, type, createdAt)
}
