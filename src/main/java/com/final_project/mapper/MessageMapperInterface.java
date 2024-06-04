package com.final_project.mapper;

import com.final_project.dto.MessageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MessageMapperInterface {

    // 메시지 저장
    @Insert("INSERT INTO messages (messagetext, senderMemNo, receiverMemNo) VALUES (#{messagetext}, #{senderMemNo}, #{receiverMemNo})")
    void insertMessage(MessageDTO message);

    // 특정 사용자에 의해 보낸 모든 메시지 조회
    @Select("SELECT messageno, messagetext, senderMemNo, receiverMemNo, mecreatedat FROM messages WHERE senderMemNo = #{senderMemNo}")
    List<MessageDTO> findAllMessagesBySenderMemNo(@Param("senderMemNo") int senderMemNo);

    // 특정 수신자에 의해 받은 모든 메시지 조회
    @Select("SELECT messageno, messagetext, senderMemNo, receiverMemNo, mecreatedat FROM messages WHERE receiverMemNo = #{receiverMemNo}")
    List<MessageDTO> findAllMessagesByReceiverMemNo(@Param("receiverMemNo") int receiverMemNo);

    // 모든 메시지 조회
    @Select("SELECT messageno, messagetext, senderMemNo, receiverMemNo, mecreatedat FROM messages")
    List<MessageDTO> findAllMessages();
}
