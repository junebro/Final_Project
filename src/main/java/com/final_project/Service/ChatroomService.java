package com.final_project.Service;

import com.final_project.entity.Chatroom;
import com.final_project.mapper.ChatRoomMapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatroomService {

    //@Autowired: 스프링의 의존성 주입 기능을 사용하여 ChatRoomMapperInterface 인스턴스를 자동으로 주입한다.
    @Autowired
    private ChatRoomMapperInterface chatRoomMapper;

    // @Transactional: 메소드가 트랜잭션 내에서 실행되어야 함을 나타냅니다. readOnly = true 옵션은 데이터베이스의 데이터를 변경하지 않고 읽기만 수행하는 작업에 최적화되어 있음을 나타낸다.
    // 채팅방 생성: 새 채팅방을 데이터베이스에 추가하고 생성된 채팅방의 ID를 반환한다.
    @Transactional
    public int createChatroom(Chatroom chatroom) {
        return chatRoomMapper.insert(chatroom);
    }

    // 채팅방 조회: 주어진 채팅방 ID에 해당하는 채팅방 정보를 데이터베이스에서 조회한다.
    @Transactional(readOnly = true)
    public Chatroom getChatroomById(Integer roomNo) {
        return chatRoomMapper.selectByRoomNo(roomNo);
    }

    // 모든 채팅방 조회: 데이터베이스에 있는 모든 채팅방의 목록을 반환한다.
    @Transactional(readOnly = true)
    public List<Chatroom> getAllChatrooms() {
        return chatRoomMapper.selectAll();
    }

    // 채팅방 업데이트: 특정 채팅방의 정보를 업데이트합니다. 업데이트 성공 시 변경된 행의 수를 반환한다..
    @Transactional
    public int updateChatroom(Chatroom chatroom) {
        return chatRoomMapper.update(chatroom);
    }

    // 채팅방 삭제: 주어진 채팅방 ID를 가진 채팅방을 데이터베이스에서 삭제합니다. 삭제 성공 시 삭제된 행의 수를 반환한다..
    @Transactional
    public int deleteChatroom(Integer roomNo) {
        return chatRoomMapper.delete(roomNo);
    }
}
