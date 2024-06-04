package com.final_project.Service;

import com.final_project.dto.MessageDTO;
import com.final_project.mapper.MessageMapperInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MessageService {

    private final MessageMapperInterface messageMapper;

    public MessageService(MessageMapperInterface messageMapper) {
        this.messageMapper = messageMapper;
    }

    @Transactional
    public void saveMessage(MessageDTO message) {
        messageMapper.insertMessage(message);
    }

    public List<MessageDTO> getMessagesBySenderMemNo(int senderMemNo) {
        return messageMapper.findAllMessagesBySenderMemNo(senderMemNo);
    }

    // 메서드 추가: 특정 사용자에 의해 받은 모든 메시지 조회
    public List<MessageDTO> getMessagesByReceiverMemNo(int receiverMemNo) {
        return messageMapper.findAllMessagesByReceiverMemNo(receiverMemNo);
    }

    public List<MessageDTO> getAllMessages() {
        return messageMapper.findAllMessages();
    }
}
