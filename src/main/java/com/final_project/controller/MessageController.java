package com.final_project.controller;

import com.final_project.dto.MessageDTO;
import com.final_project.Service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // 메시지 저장 API
    @PostMapping
    public ResponseEntity<String> saveMessage(@RequestBody MessageDTO message) {
        messageService.saveMessage(message);
        return ResponseEntity.ok("Message saved successfully");
    }

    // 특정 사용자의 모든 발신 메시지 조회 API
    @GetMapping("/sender/{senderMemNo}")
    public ResponseEntity<List<MessageDTO>> getMessagesBySenderMemNo(@PathVariable int senderMemNo) {
        List<MessageDTO> messages = messageService.getMessagesBySenderMemNo(senderMemNo);
        return ResponseEntity.ok(messages);
    }

    // 특정 사용자의 모든 수신 메시지 조회 API
    @GetMapping("/receiver/{receiverMemNo}")
    public ResponseEntity<List<MessageDTO>> getMessagesByReceiverMemNo(@PathVariable int receiverMemNo) {
        List<MessageDTO> messages = messageService.getMessagesByReceiverMemNo(receiverMemNo);
        return ResponseEntity.ok(messages);
    }

    // 모든 메시지 조회 API
    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        List<MessageDTO> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
}
