package com.backend.domain.chat.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Controller
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent

private val log = KotlinLogging.logger {}

@Controller
class WebSocketEventController {

    /**
     * WebSocket 연결 이벤트 핸들러
     */
    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectEvent) {
        StompHeaderAccessor.wrap(event.message).sessionId?.let { sessionId ->
            log.info { "WebSocket 연결됨 → 세션 ID: $sessionId" }
        }
    }

    /**
     * WebSocket 연결 해제 이벤트 핸들러
     */
    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        StompHeaderAccessor.wrap(event.message).sessionId?.let { sessionId ->
            log.info { "WebSocket 연결 해제됨 → 세션 ID: $sessionId" }
        }
    }
}
