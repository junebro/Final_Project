package com.final_project.config;

import com.final_project.filter.JwtWebSocketFilter;
import com.final_project.utility.JwtTokenProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // 이 클래스가 스프링의 구성 클래스임을 나타낸다.
@EnableWebSocketMessageBroker // 메세지브로커가 웹소켓을 통해 메세지를 처리할 수 있도록 한다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // WebSocketMessageBrokerConfigurer : 이 인터페이스를 구현하여 WebSocket 관련 설정을 정의할 수 있다.

    private final JwtTokenProvider jwtTokenProvider;

    public WebSocketConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/userchat")
                .setAllowedOrigins("http://localhost:3000")
                .addInterceptors(new JwtWebSocketFilter(jwtTokenProvider))
                .withSockJS();
        registry.addEndpoint("/adminchat")
                .setAllowedOrigins("http://localhost:3000")
                .addInterceptors(new JwtWebSocketFilter(jwtTokenProvider))
                .withSockJS();
    }
    // registerStompEndpoints(StompEndpointRegistry registry) : 클라이언트가 웹소켓에 연결할 엔드포인트를 등록한다.
    // 엔드포인트 : "/userchat", "/adminchat" 이 엔드포인트는 SockJS와 함께 사용된다.
    // 원본 허용이 "http://localhost:3000"으로 제한
    // Client는 React를 사용하기 때문에 CORS를 허용해야만

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }
    // configureMessageBroker(MessageBrokerRegistry registry) : 메세지 브로커 구성
    // "/topic" 및 "/queue"를 사용하여 간단한 메모리 기반 브로커를 활성화
    //  /topic 은 1:N의 일대다의 구독방식을 가지고 있고, /queue 는 1:1구독방식으로 일대일 메세지 전달을 할때 사용된다.
    // 클라이언트에서 "/app"으로 시작하는 모든 메시지를 처리할 것임을 설정
}
