package com.backend.global.config

import com.backend.domain.chat.handler.StompHandler
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val stompHandler: StompHandler
) : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
            .withSockJS()

        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
    }

    // 애플리케이션 내부에서 사용할 path를 지정할 수 있음
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/sub")
        registry.setApplicationDestinationPrefixes("/pub")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(stompHandler)
    }
}
