package com.backend.domain.chat.repository.kotlin

import com.backend.domain.chat.entity.ChatKt
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class ChatRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : CustomChatRepository {

    override fun findChatsByPost(postId: String): List<ChatKt> =
        postId.takeIf { it.isNotBlank() }?.let {
            Query().run {
                addCriteria(Criteria.where("postId").`is`(it))
                with(Sort.by(Sort.Direction.ASC, "_id"))
                mongoTemplate.find(this, ChatKt::class.java)
            }
        } ?: emptyList()

    override fun findChatsBefore(postId: String, lastMessageId: String, limit: Int): List<ChatKt> =
        postId.takeIf { it.isNotBlank() }?.let {
            Query().run {
                addCriteria(Criteria.where("postId").`is`(it))
                addCriteria(Criteria.where("_id").lt(lastMessageId)) // lastMessageId 이전의 메시지 가져오기
                with(Sort.by(Sort.Direction.DESC, "_id")) // 최신순 정렬 후
                limit(limit) // 최대 조회 개수 제한
                mongoTemplate.find(this, ChatKt::class.java).reversed() // 다시 오래된 순으로 정렬
            }
        } ?: emptyList()

    override fun findRecentChatsByPost(postId: String, limit: Int): List<ChatKt> =
        postId.takeIf { it.isNotBlank() }?.let {
            Query().run {
                addCriteria(Criteria.where("postId").`is`(it))
                with(Sort.by(Sort.Direction.DESC, "_id")) // 최신순 정렬
                limit(limit) // 가장 최신 limit개만 가져오기
                mongoTemplate.find(this, ChatKt::class.java).reversed() // 오래된 순으로 정렬
            }
        } ?: emptyList()
}
