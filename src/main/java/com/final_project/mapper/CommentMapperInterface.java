package com.final_project.mapper;

import com.final_project.dto.CommentDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapperInterface {

    // 특정 게시물의 모든 댓글을 조회
    @Select("SELECT c.*, m.memberNick FROM comments c JOIN tmem m ON c.memNo = m.memNo WHERE c.bono = #{bono}")
    List<CommentDTO> selectCommentsByBono(@Param("bono") Integer bono);

    // 새로운 댓글을 데이터베이스에 삽입
    @Insert("INSERT INTO comments (bono, memNo, comContent, com_create_at, com_update_at) " +
            "VALUES (#{comment.bono}, #{comment.memNo}, #{comment.comContent}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "comment.comno")
    int insertComment(@Param("comment") CommentDTO comment);

    // 기존 댓글을 업데이트
    @Update("UPDATE comments SET comContent = #{comment.comContent}, com_update_at = NOW() WHERE comno = #{comment.comno}")
    int updateComment(@Param("comment") CommentDTO comment);

    // 댓글 삭제
    @Delete("DELETE FROM comments WHERE comno = #{comno}")
    int deleteComment(@Param("comno") Integer comno);
}
