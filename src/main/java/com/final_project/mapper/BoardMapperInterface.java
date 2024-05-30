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
    @Select("SELECT b.bono, b.mbrno, b.botitle, b.bocontent, b.BO_CREATE_AT, b.boimage01, b.boimage02, b.boimage03, b.thumb_boimage01, b.thumb_boimage02, b.thumb_boimage03, " +
            "COALESCE(c.ascommentCount, 0) AS commentCount, b.viewCount, b.likeCount " +
            "FROM boards b " +
            "LEFT JOIN (SELECT bono, COUNT(*) AS ascommentCount FROM comments GROUP BY bono) c ON b.bono = c.bono")
    List<Board> SelectAll();



    // 게시물 삽입
    // no 컬럼은 auto increment 옵션에 의하여 자동으로 채워진다.
    // NOW()는 MySQL의 현재 시간을 반환하는 함수
    @Insert("INSERT INTO boards (mbrno, botitle, bocontent, BO_CREATE_AT, boimage01, boimage02, boimage03, thumb_boimage01, thumb_boimage02, thumb_boimage03, viewCount, likeCount) " +
            "VALUES (#{board.mbrno}, #{board.botitle}, #{board.bocontent}, NOW(), #{board.boimage01}, #{board.boimage02}, #{board.boimage03}, #{board.thumb_boimage01}, #{board.thumb_boimage02}, #{board.thumb_boimage03}, 0, 0)")
    int Insert(@Param("board") final Board board);;

    // 게시물 상세보기
    @Select("SELECT b.bono, b.mbrno, b.botitle, b.bocontent, b.BO_CREATE_AT, b.boimage01, b.boimage02, b.boimage03, b.thumb_boimage01, b.thumb_boimage02, b.thumb_boimage03, " +
            "COALESCE(c.ascommentCount, 0) AS commentCount, b.viewCount, b.likeCount " +
            "FROM boards b " +
            "LEFT JOIN (SELECT bono, COUNT(*) AS ascommentCount FROM comments GROUP BY bono) c ON b.bono = c.bono " +
            "WHERE b.bono = #{bono}")
    Board SelectOne(@Param("bono") Integer bono);


    // 게시물 업데이트
    @Update("UPDATE boards SET mbrno = #{board.mbrno}, botitle = #{board.botitle}, bocontent = #{board.bocontent}, boimage01 = #{board.boimage01}, boimage02 = #{board.boimage02}, boimage03 = #{board.boimage03}, viewCount = #{board.viewCount}, likeCount = #{board.likeCount} WHERE bono = #{board.bono}")
    int Update(@Param("board") Board board);

    // 게시물 삭제
    @Delete("DELETE FROM boards WHERE bono = #{bono}")
    int Delete(@Param("bono") Integer bono);

//    게시글의 총 조회수, 댓글 수, 좋아요 수를 데이터베이스에서 직접 계산하여 불러온다.
//    게시글을 삽입하거나 업데이트할 때 이러한 수치들도 함께 처리한다.
//    게시글 삽입 시에는 조회수와 좋아요 수를 0으로 초기화한다.

    // 조회수 업데이트
    @Update("UPDATE boards SET viewCount = viewCount + 1 WHERE bono = #{bono}")
    int updateViewCount(Integer bono);
}
