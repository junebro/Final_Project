package com.final_project.mapper;

import com.final_project.entity.Chatroom;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatRoomMapperInterface {

    // 채팅방 생성
    @Insert("INSERT INTO chatrooms (MEMBERID, ADMINID, ROOMCREATEAT) " +
            "VALUES (#{chatroom.memberId}, #{chatroom.adminId}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "chatroom.roomNo")
    int insert(@Param("chatroom") Chatroom chatroom);

    // 특정 채팅방 조회
    @Select("SELECT * FROM chatrooms WHERE ROOMNO = #{roomNo}")
    Chatroom selectByRoomNo(@Param("roomNo") Integer roomNo);

    // 모든 채팅방 조회
    @Select("SELECT * FROM chatrooms")
    List<Chatroom> selectAll();

    // 채팅방 정보 업데이트
    @Update("UPDATE chatrooms SET MEMBERID = #{chatroom.memberId}, ADMINID = #{chatroom.adminId} WHERE ROOMNO = #{chatroom.roomNo}")
    int update(@Param("chatroom") Chatroom chatroom);

    // 채팅방 삭제
    @Delete("DELETE FROM chatrooms WHERE ROOMNO = #{roomNo}")
    int delete(@Param("roomNo") Integer roomNo);
}

