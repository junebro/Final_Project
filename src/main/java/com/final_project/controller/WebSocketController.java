//package com.final_project.controller;
//
//import com.final_project.entity.Message;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class WebSocketController {
//    // System.out.println 대신 로거를 사용하여 더 세밀한 로깅과 오류 추적을 할 수 있다.
//    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
//
//
//    @MessageMapping("/chat")
//    @SendTo("/topic/messages")
//    public Message handleChatMessage(@Payload Message message) {
//        logger.info("Received message: {}", message.getMessageText());
//        return message;
//    }
//}
