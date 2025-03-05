package com.backend.domain.chat.service

import com.backend.domain.chat.converter.ChatConverterKt
import com.backend.domain.chat.dto.request.ChatRequestKt
import com.backend.domain.chat.dto.response.ChatResponseKt
import com.backend.domain.chat.dto.response.ChatResponsesKt
import com.backend.domain.chat.repository.ChatRepository
import com.backend.domain.user.repository.UserRepository
import com.backend.global.exception.GlobalErrorCode
import com.backend.global.exception.GlobalException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Duration

private val log = KotlinLogging.logger {}

@Service
class ChatServiceKt(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository,
    private val redisTemplate: RedisTemplate<String, String>
) {
    companion object {
        private const val ACTIVE_CHAT_TTL_MINUTES = 15L
        private const val INACTIVE_CHAT_TTL_MINUTES = 60L
        private const val CHAT_THRESHOLD = 50
        private const val REDIS_PREFIX = "chatList:"
        private const val MAX_CACHED_MESSAGES = 200L
    }

    private val objectMapper = jacksonObjectMapper()

    fun save(chatRequest: ChatRequestKt, postId: String): ChatResponseKt {
        val user = userRepository.findByIdOrNull(chatRequest.userId.toLong())
            ?: throw GlobalException(GlobalErrorCode.USER_NOT_FOUND)

        val chatResponse = chatRepository.save(
            ChatConverterKt.toChat(chatRequest, postId, user)
        ).let { ChatConverterKt.toChatResponse(it) }

        saveChatToRedis(postId, chatResponse)
        extendTTL(postId)
        return chatResponse
    }

    fun getAllByPostId(postId: String): ChatResponsesKt {
        val redisKey = redisKey(postId)
        val cachedChats = redisTemplate.opsForList().range(redisKey, 0, -1)
            ?.mapNotNull { parseJson(it) } ?: emptyList()

        if (cachedChats.isNotEmpty()) {
            log.info { "From Redis (TTL: ${getDynamicTTL(postId)}분) → postId = $postId" }

            val oldestCachedId = cachedChats.minOfOrNull { it.id }

            val missingChats = if (oldestCachedId != null) {
                chatRepository.findChatsBefore(postId, oldestCachedId, 50)
                    .map { ChatConverterKt.toChatResponse(it) }
            } else {
                emptyList()
            }

            if (missingChats.isNotEmpty()) {
                saveChatsToRedis(postId, missingChats)
            }
            return ChatResponsesKt(missingChats + cachedChats)
        }

        log.info { "Cache Expired → Fetching from MongoDB → postId = $postId" }
        val chats = chatRepository.findRecentChatsByPost(postId, 100)
            .map { ChatConverterKt.toChatResponse(it) }

        saveChatsToRedis(postId, chats)
        return ChatResponsesKt(chats)
    }

    private fun saveChatToRedis(postId: String, chatResponse: ChatResponseKt) {
        val redisKey = redisKey(postId)
        runCatching {
            objectMapper.writeValueAsString(chatResponse)
        }.onSuccess { json ->
            redisTemplate.opsForList().rightPush(redisKey, json)
            redisTemplate.opsForList().trim(redisKey, -MAX_CACHED_MESSAGES, -1)
            extendTTL(postId)
        }.onFailure {
            log.error(it) { "Redis 저장 중 JSON 변환 오류" }
        }
    }

    private fun saveChatsToRedis(postId: String, chats: List<ChatResponseKt>) {
        if (chats.isEmpty()) return
        val redisKey = redisKey(postId)
        chats.forEach { chat ->
            runCatching {
                objectMapper.writeValueAsString(chat)
            }.onSuccess { json ->
                redisTemplate.opsForList().rightPush(redisKey, json)
            }.onFailure {
                log.error(it) { "Redis 저장 중 JSON 변환 오류" }
            }
        }
        redisTemplate.opsForList().trim(redisKey, -MAX_CACHED_MESSAGES, -1)
    }

    private fun parseJson(json: String): ChatResponseKt? =
        runCatching { objectMapper.readValue<ChatResponseKt>(json) }
            .onFailure { log.error(it) { "JSON 변환 오류" } }
            .getOrNull()

    private fun getDynamicTTL(postId: String): Long =
        (redisTemplate.opsForList().size(redisKey(postId)) ?: 0)
            .let { if (it >= CHAT_THRESHOLD) ACTIVE_CHAT_TTL_MINUTES else INACTIVE_CHAT_TTL_MINUTES }

    private fun extendTTL(postId: String) {
        val currentTTL = redisTemplate.getExpire(redisKey(postId))
        redisTemplate.expire(redisKey(postId), Duration.ofMinutes(getDynamicTTL(postId)))
        val newTTL = redisTemplate.getExpire(redisKey(postId))
        log.info { "Redis Cache TTL ${currentTTL / 60}분 -> ${newTTL / 60}분 연장 (postNum = $postId)" }
    }


    private fun redisKey(postId: String) = "$REDIS_PREFIX$postId"
}