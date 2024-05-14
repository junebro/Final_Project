package com.final_project.mapper;

import com.final_project.entity.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapperInterface {

    // 총 게시물 수를 조회하는 메소드
    @Select("SELECT COUNT(*) FROM boards")
    long count();

    @Select(value = "select * from boards ")
    List<Board> SelectAll();

    // no 컬럼은 auto increment 옵션에 의하여 자동으로 채워진다.
    @Insert("INSERT INTO boards (mbrno, botitle, bocontent) VALUES (#{board.mbrno}, #{board.botitle}, #{board.bocontent}) ")
    int Insert(@Param("board") final Board board);

    @Select("select * from boards where bono = #{bono} ") // 게시물 상세보기
    Board SelectOne(@Param("bono") Integer bono);

    @Update("UPDATE boards SET mbrno = #{board.mbrno}, botitle = #{board.botitle}, bocontent = #{board.bocontent} WHERE bono = #{board.bono} ")
    int Update(@Param("board") Board board);

    @Delete("delete from boards where bono = #{bono} ")
    int Delete(@Param("bono") Integer bono);
}
