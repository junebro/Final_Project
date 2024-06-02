package com.final_project.filter;

import com.final_project.utility.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Slf4j
public class JwtWebSocketFilter implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtWebSocketFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        List<String> authHeaders = request.getHeaders().get("Authorization");

        if (authHeaders == null || authHeaders.isEmpty()) {
            log.debug("Missing Authorization header");
            return false;
        }

        String token = authHeaders.get(0).substring(7);
        log.debug("Extracted token: " + token);

        if (jwtTokenProvider.validateToken(token)) {
            log.debug("Token is valid");
            return true;
        } else {
            log.debug("Token is invalid");
            return false;
        }
    }


    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // No action needed after handshake
    }
}
