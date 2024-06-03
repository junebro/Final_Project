package com.final_project.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    public void processMessageFromClient(ChatMessage message) {
        log.info("Received message from client: {}", message);
        messagingTemplate.convertAndSend("/topic/messages", message);
    }

    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        log.info("Sending message to clients: {}", message);
        return message;
    }

    public static class ChatMessage {
        private String messageText;

        // getter and setter
        public String getMessageText() {
            return messageText;
        }

        public void setMessageText(String messageText) {
            this.messageText = messageText;
        }

        @Override
        public String toString() {
            return "ChatMessage{messageText='" + messageText + "'}";
        }
    }
}
