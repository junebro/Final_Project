package com.final_project.mapper;

import com.final_project.entity.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapperInterface {

    // 총 게시물 수를 조회하는 메소드
    @Select("SELECT COUNT(*) FROM boards")
    long count();

    // 모든 게시물을 조회
    @Select(value = "select * from boards ")
    List<Board> SelectAll();

    // 게시물 삽입
    // no 컬럼은 auto increment 옵션에 의하여 자동으로 채워진다.
    // NOW()는 MySQL의 현재 시간을 반환하는 함수
    @Insert("INSERT INTO boards (mbrno, botitle, bocontent, BO_CREATE_AT, boimage01, boimage02, boimage03, thumb_boimage01, thumb_boimage02, thumb_boimage03) " +
            "VALUES (#{board.mbrno}, #{board.botitle}, #{board.bocontent}, NOW(), #{board.boimage01}, #{board.boimage02}, #{board.boimage03}, #{board.thumb_boimage01}, #{board.thumb_boimage02}, #{board.thumb_boimage03})")
    int Insert(@Param("board") final Board board);

    // 게시물 상세 조회
    @Select("SELECT bono, mbrno, botitle, bocontent, BO_CREATE_AT, boimage01, boimage02, boimage03 FROM boards WHERE bono = #{bono}")
    Board SelectOne(@Param("bono") Integer bono);

    // 게시물 업데이트
    @Update("UPDATE boards SET mbrno = #{board.mbrno}, botitle = #{board.botitle}, bocontent = #{board.bocontent}, boimage01 = #{board.boimage01}, boimage02 = #{board.boimage02}, boimage03 = #{board.boimage03} WHERE bono = #{board.bono}")
    int Update(@Param("board") Board board);

    // 게시물 삭제
    @Delete("DELETE FROM boards WHERE bono = #{bono}")
    int Delete(@Param("bono") Integer bono);
}
