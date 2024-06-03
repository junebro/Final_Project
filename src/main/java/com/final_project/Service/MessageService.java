package com.final_project.Service;

import com.final_project.dto.MessageDTO;
import com.final_project.mapper.MessageMapperInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MessageService {

    private final MessageMapperInterface messageMapper;

    // 의존성 주입을 통해 MessageMapperInterface를 연결합니다.
    public MessageService(MessageMapperInterface messageMapper) {
        this.messageMapper = messageMapper;
    }

    // 메시지 저장 로직
    @Transactional
    public void saveMessage(MessageDTO message) {
        messageMapper.insertMessage(message);
    }

    // 특정 사용자에 의해 보낸 모든 메시지 조회
    public List<MessageDTO> getMessagesBySenderMemNo(int senderMemNo) {
        return messageMapper.findAllMessagesBySenderMemNo(senderMemNo);
    }

    // 모든 메시지 조회
    public List<MessageDTO> getAllMessages() {
        return messageMapper.findAllMessages();
    }
}
