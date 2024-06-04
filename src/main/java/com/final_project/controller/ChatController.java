package com.final_project.controller;

import com.final_project.Service.MessageService;
import com.final_project.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    // WebSocket 세션에 대한 인증 정보를 저장할 맵
    private Map<String, Authentication> sessionAuthMap = new ConcurrentHashMap<>();

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        Principal user = event.getUser();
        if (user != null) {
            String sessionId = event.getMessage().getHeaders().get("simpSessionId", String.class);
            sessionAuthMap.put(sessionId, (Authentication) user);
        }
    }

    @MessageMapping("/chat")
    public void processMessageFromClient(ChatMessage message, Principal principal) {
        if (principal == null) {
            log.error("Principal object is null");
            return;
        }

        Authentication authentication = (Authentication) principal;
        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("Authentication object is null or not authenticated");
            return;
        }

        Integer senderMemNo = Integer.parseInt(authentication.getName()); // 발신자 번호 추출
        Integer receiverMemNo = message.getReceiverMemNo(); // 메시지 객체에서 수신자 번호를 추출

        log.info("Received message from client: {}", message);
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessagetext(message.getMessageText());
        messageDTO.setSenderMemNo(senderMemNo);
        messageDTO.setReceiverMemNo(receiverMemNo);

        messageService.saveMessage(messageDTO);

        // 수신자의 sessionId 찾기
        String sessionID = findSessionIdByMemberNo(receiverMemNo);
        if (sessionID != null) {
            messagingTemplate.convertAndSendToUser(sessionID, "/queue/reply", message);
        }
    }

    private String findSessionIdByMemberNo(Integer memNo) {
        return sessionAuthMap.entrySet().stream()
                .filter(entry -> entry.getValue().getName().equals(String.valueOf(memNo)))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public static class ChatMessage {
        private String messageText;
        private Integer receiverMemNo; // 추가된 필드: 수신자 번호

        public String getMessageText() {
            return messageText;
        }

        public void setMessageText(String messageText) {
            this.messageText = messageText;
        }

        public Integer getReceiverMemNo() {
            return receiverMemNo;
        }

        public void setReceiverMemNo(Integer receiverMemNo) {
            this.receiverMemNo = receiverMemNo;
        }

        @Override
        public String toString() {
            return "ChatMessage{messageText='" + messageText + "', receiverMemNo=" + receiverMemNo + "}";
        }
    }
}
