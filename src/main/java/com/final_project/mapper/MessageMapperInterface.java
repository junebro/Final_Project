package com.final_project.mapper;

import com.final_project.entity.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapperInterface {

    // 메시지 삽입
    @Insert("INSERT INTO messages (ROOMNO, SENDERNO, MESSAGETEXT, MCREATEDAT) " +
            "VALUES (#{message.roomNo}, #{message.senderNo}, #{message.messageText}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "message.messageNo")
    int insert(@Param("message") Message message);

    // 특정 메시지 조회
    @Select("SELECT * FROM messages WHERE MESSAGENO = #{messageNo}")
    Message selectByMessageNo(@Param("messageNo") Integer messageNo);

    // 특정 채팅방의 모든 메시지 조회
    @Select("SELECT * FROM messages WHERE ROOMNO = #{roomNo}")
    List<Message> selectByRoomNo(@Param("roomNo") Integer roomNo);

    // 메시지 업데이트
    @Update("UPDATE messages SET MESSAGETEXT = #{message.messageText} WHERE MESSAGENO = #{message.messageNo}")
    int update(@Param("message") Message message);

    // 메시지 삭제
    @Delete("DELETE FROM messages WHERE MESSAGENO = #{messageNo}")
    int delete(@Param("messageNo") Integer messageNo);
}
