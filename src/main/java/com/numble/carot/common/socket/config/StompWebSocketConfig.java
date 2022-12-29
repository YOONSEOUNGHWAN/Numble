package com.numble.carot.common.socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * WebSocket 또는 SockJS Client가 웹소켓 HandShake 커넥션을 생성할 경로.
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chat")
                .setAllowedOrigins("http://localhost:8080")
                .withSockJS();
    }

    /**
     * /test경로로 시작하는 STOMP메세지의 destination 헤더는
     * @Controller 객체의 @MessageMapping 메서드로 라우팅된다.
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub");
    }
}
