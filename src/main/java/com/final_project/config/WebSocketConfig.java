package com.final_project.config;

import com.final_project.utility.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/userchat")
                .setAllowedOrigins("http://localhost:3000")
                .addInterceptors(httpSessionHandshakeInterceptor())
                .setHandshakeHandler(defaultHandshakeHandler())
                .withSockJS();
        registry.addEndpoint("/adminchat")
                .setAllowedOrigins("http://localhost:3000")
                .addInterceptors(httpSessionHandshakeInterceptor())
                .setHandshakeHandler(defaultHandshakeHandler())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }

    private HandshakeInterceptor httpSessionHandshakeInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                String query = request.getURI().getQuery();
                if (query != null && query.startsWith("token=")) {
                    String token = query.substring(6);
                    if (jwtTokenProvider.validateToken(token)) {
                        Authentication auth = jwtTokenProvider.getAuthentication(token);
                        attributes.put("auth", auth);
                        return true;
                    }
                }
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
                // 후처리 로직
            }
        };
    }

    private DefaultHandshakeHandler defaultHandshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                return (Principal) attributes.get("auth");
            }
        };
    }
}