package com.final_project.Service;

import com.final_project.entity.Message;
import com.final_project.mapper.MessageMapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageMapperInterface messageMapper;

    // 새 메시지를 생성하고 데이터베이스에 저장한다.
    // 성공적으로 삽입된 경우 삽입된 행의 수를 반환한다.
    @Transactional
    public int createMessage(Message message) {
        return messageMapper.insert(message);
    }

    // 주어진 메시지 ID에 해당하는 메시지를 데이터베이스에서 조회한다.
    // 조회된 메시지 객체를 반환한다.
    @Transactional(readOnly = true)
    public Message getMessageById(Integer messageNo) {
        return messageMapper.selectByMessageNo(messageNo);
    }

    // 주어진 채팅방 번호에 해당하는 모든 메시지를 조회한다.
    // 조회된 메시지 리스트를 반환한다.
    @Transactional(readOnly = true)
    public List<Message> getMessagesByRoomNo(Integer roomNo) {
        return messageMapper.selectByRoomNo(roomNo);
    }

    // 특정 메시지의 내용을 업데이트한다.
    // 업데이트 성공 시 변경된 행의 수를 반환한다.
    @Transactional
    public int updateMessage(Message message) {
        return messageMapper.update(message);
    }

    // 주어진 메시지 ID를 가진 메시지를 데이터베이스에서 삭제한다.
    // 삭제 성공 시 삭제된 행의 수를 반환한다.
    @Transactional
    public int deleteMessage(Integer messageNo) {
        return messageMapper.delete(messageNo);
    }
}
