//package com.backend.global.config
//
//import com.backend.global.redis.service.RedisSubscriberKt
//import com.fasterxml.jackson.databind.ObjectMapper
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.redis.connection.RedisConnectionFactory
//import org.springframework.data.redis.core.RedisTemplate
//import org.springframework.data.redis.listener.ChannelTopic
//import org.springframework.data.redis.listener.PatternTopic
//import org.springframework.data.redis.listener.RedisMessageListenerContainer
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
//import org.springframework.data.redis.serializer.StringRedisSerializer
//
//@Configuration
//@EnableRedisRepositories
//class RedisConfig {
//
//    /**
//     * RedisTemplate 설정
//     * - Key: String 타입
//     * - Value: JSON 직렬화 (GenericJackson2JsonRedisSerializer)
//     */
//    @Bean
//    fun redisTemplate(factory: RedisConnectionFactory, objectMapper: ObjectMapper): RedisTemplate<String, Any> =
//        RedisTemplate<String, Any>().apply {
//            setConnectionFactory(factory)
//            keySerializer = StringRedisSerializer()
//            valueSerializer = GenericJackson2JsonRedisSerializer(objectMapper)
//        }
//
//    /**
//     * Redis Pub/Sub 메시지 리스너 컨테이너
//     * - 모든 `postNum:*` 패턴을 구독 가능
//     */
//    @Bean
//    fun redisMessageListenerContainer(
//        connectionFactory: RedisConnectionFactory,
//        messageListenerAdapter: MessageListenerAdapter
//    ): RedisMessageListenerContainer =
//        RedisMessageListenerContainer().apply {
//            setConnectionFactory(connectionFactory)
//            addMessageListener(messageListenerAdapter, PatternTopic("postNum:*"))
//        }
//
//    /**
//     * Redis 메시지 리스너 어댑터
//     * - RedisSubscriber의 `onMessage` 메서드와 연결
//     */
//    @Bean
//    fun messageListenerAdapter(redisSubscriber: RedisSubscriberKt): MessageListenerAdapter =
//        MessageListenerAdapter(redisSubscriber, "onMessage")
//
//    /**
//     * 기본 Redis Topic 설정
//     * - 실제 사용 시 동적으로 변경 가능
//     */
//    @Bean
//    fun channelTopic(): ChannelTopic = ChannelTopic("postNum:default")
//}
