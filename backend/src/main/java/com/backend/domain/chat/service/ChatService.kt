package com.backend.domain.chat.service

import com.backend.domain.chat.converter.ChatConverter
import com.backend.domain.chat.dto.request.ChatRequest
import com.backend.domain.chat.dto.response.ChatResponse
import com.backend.domain.chat.dto.response.ChatResponses
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
class ChatService(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository,
    private val redisTemplate: RedisTemplate<String, String>
) {
    companion object {
        private const val ACTIVE_CHAT_TTL_MINUTES = 15L // 활성 채팅방 TTL (15분)
        private const val INACTIVE_CHAT_TTL_MINUTES = 60L // 비활성 채팅방 TTL (60분)
        private const val REDIS_PREFIX = "chatList:" // Redis 키 Prefix
        private const val CHAT_THRESHOLD = 50 // 활성 채팅의 기준이 되는 최소 메시지 수
        private const val MAX_CACHED_MESSAGES = 200L // Redis에 저장할 최대 메시지 개수
    }

    private val objectMapper = jacksonObjectMapper()

    /**
     * 채팅 메시지를 저장하고 Redis에 캐싱
     *
     * @param chatRequest 사용자가 전송한 채팅 요청 데이터
     * @param postId 게시글 ID
     * @return 저장된 채팅 메시지 응답 데이터
     */
    fun save(chatRequest: ChatRequest, postId: String): ChatResponse {
        val user = userRepository.findByIdOrNull(chatRequest.userId.toLong())
            ?: throw GlobalException(GlobalErrorCode.USER_NOT_FOUND)

        val chatResponse = chatRepository.save(
            ChatConverter.toChat(chatRequest, postId, user)
        ).let { ChatConverter.toChatResponse(it) }

        // Redis에 저장 후 TTL 연장
        saveChatToRedis(postId, chatResponse)
        extendTTL(postId)

        return chatResponse
    }

    /**
     * 특정 게시글의 채팅 목록을 조회 (Redis 캐싱 활용)
     *
     * @param postId 게시글 ID
     * @return 채팅 메시지 목록 응답
     */
    fun getAllByPostId(postId: String): ChatResponses {
        val redisKey = redisKey(postId)

        // Redis에서 채팅 데이터 조회
        val cachedChats = redisTemplate.opsForList().range(redisKey, 0, -1)
            ?.mapNotNull { parseJson(it) } ?: emptyList()

        if (cachedChats.isNotEmpty()) {
            log.info { "From Redis (TTL: ${getDynamicTTL(postId)}분) → postId = $postId" }

            val oldestCachedId = cachedChats.minOfOrNull { it.id }

            // Redis에 없는 과거 채팅이 있는 경우 DB에서 조회
            val missingChats = if (oldestCachedId != null) {
                chatRepository.findChatsBefore(postId, oldestCachedId, 50)
                    .map { ChatConverter.toChatResponse(it) }
            } else {
                emptyList()
            }

            // 누락된 메시지를 올바른 순서로 Redis에 추가
            if (missingChats.isNotEmpty()) {
                saveChatsToRedis(postId, missingChats)
            }
            return ChatResponses(missingChats + cachedChats)
        }

        // Redis에 데이터가 없으면 DB에서 최근 채팅을 가져와 캐싱
        log.info { "Cache Expired → Fetching from MongoDB → postId = $postId" }
        val chats = chatRepository.findRecentChatsByPost(postId, 100)
            .map { ChatConverter.toChatResponse(it) }

        saveChatsToRedis(postId, chats)
        return ChatResponses(chats)
    }

    /**
     * 새로운 채팅 메시지를 Redis에 저장
     *
     * @param postId 게시글 ID
     * @param chatResponse 저장할 채팅 데이터
     */
    private fun saveChatToRedis(postId: String, chatResponse: ChatResponse) {
        val redisKey = redisKey(postId)
        runCatching {
            objectMapper.writeValueAsString(chatResponse)
        }.onSuccess { json ->
            redisTemplate.opsForList().rightPush(redisKey, json)
            redisTemplate.opsForList().trim(redisKey, -MAX_CACHED_MESSAGES, -1) // 최대 개수 유지
            extendTTL(postId)
        }.onFailure {
            log.error(it) { "Redis 저장 중 JSON 변환 오류" }
        }
    }

    /**
     * 여러 개의 채팅 메시지를 Redis에 저장
     *
     * @param postId 게시글 ID
     * @param chats 저장할 채팅 리스트
     */
    private fun saveChatsToRedis(postId: String, chats: List<ChatResponse>) {
        if (chats.isEmpty()) return
        val redisKey = redisKey(postId)

        // 과거의 메시지를 기존 메시지 앞에 추가
        chats.asReversed().forEach { chat ->
            runCatching {
                objectMapper.writeValueAsString(chat)
            }.onSuccess { json ->
                redisTemplate.opsForList().leftPush(redisKey, json) // 과거 메시지를 앞에 추가
            }.onFailure {
                log.error(it) { "Redis 저장 중 JSON 변환 오류" }
            }
        }
        redisTemplate.opsForList().trim(redisKey, -MAX_CACHED_MESSAGES, -1)
    }

    /**
     * JSON 문자열을 `ChatResponse` 객체로 변환
     *
     * @param json 변환할 JSON 문자열
     * @return 변환된 `ChatResponse` 객체 (변환 실패 시 `null`)
     */
    private fun parseJson(json: String): ChatResponse? =
        runCatching { objectMapper.readValue<ChatResponse>(json) }
            .onFailure { log.error(it) { "JSON 변환 오류" } }
            .getOrNull()

    /**
     * Redis에서 TTL을 동적으로 계산
     *
     * @param postId 게시글 ID
     * @return 동적으로 결정된 TTL (분 단위)
     */
    private fun getDynamicTTL(postId: String): Long =
        (redisTemplate.opsForList().size(redisKey(postId)) ?: 0)
            .let { if (it >= CHAT_THRESHOLD) ACTIVE_CHAT_TTL_MINUTES else INACTIVE_CHAT_TTL_MINUTES }

    /**
     * Redis에 저장된 채팅 데이터의 TTL을 연장
     *
     * @param postId 게시글 ID
     */
    private fun extendTTL(postId: String) {
        val redisKey = redisKey(postId)
        val currentTTL = redisTemplate.getExpire(redisKey) // getExpire()는 항상 Long을 반환 (null 안전)
        val newTTL = Duration.ofMinutes(getDynamicTTL(postId))

        if (currentTTL <= 0 || currentTTL < newTTL.seconds) {
            redisTemplate.expire(redisKey, newTTL)
            log.info { "Redis Cache TTL 갱신됨: ${currentTTL / 60}분 → ${newTTL.toMinutes()}분 (postNum = $postId)" }
        } else {
            log.info { "Redis TTL 유지: ${currentTTL / 60}분 (postNum = $postId)" }
        }
    }




    /**
     * Redis 키를 생성
     *
     * @param postId 게시글 ID
     * @return Redis 저장용 키
     */
    private fun redisKey(postId: String) = "$REDIS_PREFIX$postId"
}
