package com.final_project.controller;

import com.final_project.entity.Chatroom;
import com.final_project.Service.ChatroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatroomController {

    @Autowired
    private ChatroomService chatroomService;

    // POST: 새 채팅방을 생성하고, 생성된 채팅방 정보를 반환한다.
    @PostMapping
    public ResponseEntity<Chatroom> createChatroom(@RequestBody Chatroom chatroom) {
        int roomId = chatroomService.createChatroom(chatroom);
        return ResponseEntity.ok().body(chatroomService.getChatroomById(roomId));
    }

    // GET: 주어진 ID에 해당하는 채팅방의 상세 정보를 조회한다.
    @GetMapping("/{roomNo}")
    public ResponseEntity<Chatroom> getChatroomById(@PathVariable Integer roomNo) {
        Chatroom chatroom = chatroomService.getChatroomById(roomNo);
        return ResponseEntity.ok().body(chatroom);
    }

    // GET: 시스템에 있는 모든 채팅방의 목록을 반환한다.
    @GetMapping
    public ResponseEntity<List<Chatroom>> getAllChatrooms() {
        List<Chatroom> chatrooms = chatroomService.getAllChatrooms();
        return ResponseEntity.ok().body(chatrooms);
    }

    // PUT: 주어진 ID에 해당하는 채팅방의 정보를 업데이트하고, 업데이트된 정보를 반환한다.
    @PutMapping("/{roomNo}")
    public ResponseEntity<Chatroom> updateChatroom(@PathVariable Integer roomNo, @RequestBody Chatroom chatroom) {
        chatroom.setRoomNo(roomNo);
        chatroomService.updateChatroom(chatroom);
        return ResponseEntity.ok().body(chatroom);
    }

    // DELETE: 주어진 ID에 해당하는 채팅방을 삭제하고, 성공적인 삭제를 나타내는 응답을 반환한다.
    @DeleteMapping("/{roomNo}")
    public ResponseEntity<Void> deleteChatroom(@PathVariable Integer roomNo) {
        chatroomService.deleteChatroom(roomNo);
        return ResponseEntity.ok().build();
    }
}
