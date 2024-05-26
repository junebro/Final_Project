package com.final_project.controller;

import com.final_project.entity.Message;
import com.final_project.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // POST: 새로운 메시지를 생성하고, 생성된 메시지 정보를 반환한다.
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        messageService.createMessage(message);
        return ResponseEntity.ok().body(message);
    }

    // GET: 주어진 메시지 ID로 메시지를 조회하고, 조회된 메시지를 반환한다.
    @GetMapping("/{messageNo}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageNo) {
        Message message = messageService.getMessageById(messageNo);
        return ResponseEntity.ok().body(message);
    }

    // GET: 특정 채팅방의 모든 메시지를 조회하고, 조회된 메시지 리스트를 반환한다.
    @GetMapping("/room/{roomNo}")
    public ResponseEntity<List<Message>> getMessagesByRoomNo(@PathVariable Integer roomNo) {
        List<Message> messages = messageService.getMessagesByRoomNo(roomNo);
        return ResponseEntity.ok().body(messages);
    }

    // PUT: 주어진 메시지 ID에 해당하는 메시지의 정보를 업데이트하고, 업데이트된 메시지 정보를 반환한다.
    @PutMapping("/{messageNo}")
    public ResponseEntity<Message> updateMessage(@PathVariable Integer messageNo, @RequestBody Message message) {
        message.setMessageNo(messageNo);
        messageService.updateMessage(message);
        return ResponseEntity.ok().body(message);
    }

    // DELETE: 주어진 메시지 ID에 해당하는 메시지를 삭제하고, 성공적인 삭제를 나타내는 응답을 반환한다.
    @DeleteMapping("/{messageNo}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Integer messageNo) {
        messageService.deleteMessage(messageNo);
        return ResponseEntity.ok().build();
    }
}
