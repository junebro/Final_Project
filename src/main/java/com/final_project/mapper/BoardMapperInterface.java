package com.final_project.mapper;

import com.final_project.dto.BoardDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapperInterface {

    // 총 게시물 수를 조회하는 메소드
    @Select("SELECT COUNT(*) FROM boards")
    long count();

    // 모든 게시물을 조회
    @Select("<script>" +
            "SELECT b.bono, b.memNo, b.botitle, b.bocontent, b.BO_CREATE_AT, b.boimage01, b.boimage02, b.boimage03, b.thumb_boimage01, b.thumb_boimage02, b.thumb_boimage03, " +
            "m.memberNick, " +
            "COALESCE(c.ascommentCount, 0) AS commentCount, b.viewCount, b.likeCount " +
            "FROM boards b " +
            "left JOIN tmem m ON b.memNo = m.memNo " +
            "LEFT JOIN (SELECT bono, COUNT(*) AS ascommentCount FROM comments GROUP BY bono) c ON b.bono = c.bono " +
            "<choose>" +
            "   <when test='orderBy == \"recent\"'>" +
            "       ORDER BY b.BO_CREATE_AT DESC" +
            "   </when>" +
            "   <when test='orderBy == \"like\"'>" +
            "       ORDER BY b.likeCount DESC" +
            "   </when>" +
            "   <when test='orderBy == \"comment\"'>" +
            "       ORDER BY c.ascommentCount DESC" +
            "   </when>" +
            "   <otherwise>" +
            "       ORDER BY b.BO_CREATE_AT DESC" +
            "   </otherwise>" +
            "</choose>" +
            "</script>")
    List<BoardDTO> SelectAll(@Param("orderBy") String orderBy);

    // 게시물 검색
    @Select("SELECT b.bono, b.memNo, b.botitle, b.bocontent, b.BO_CREATE_AT, b.boimage01, b.boimage02, b.boimage03, b.thumb_boimage01, b.thumb_boimage02, b.thumb_boimage03, " +
            "m.memberNick " +
            "FROM boards b " +
            "JOIN tmem m ON b.memNo = m.memNo " +
            "WHERE botitle LIKE CONCAT('%', #{searchKeyword}, '%') OR bocontent LIKE CONCAT('%', #{searchKeyword}, '%')")
    List<BoardDTO> searchByTitleOrContent(@Param("searchKeyword") String searchKeyword);

    // 게시물 삽입
    @Insert("INSERT INTO boards (memNo, botitle, bocontent, BO_CREATE_AT, boimage01, boimage02, boimage03, thumb_boimage01, thumb_boimage02, thumb_boimage03, viewCount, likeCount) " +
            "VALUES (#{memNo}, #{botitle}, #{bocontent}, NOW(), #{boimage01}, #{boimage02}, #{boimage03}, #{thumb_boimage01}, #{thumb_boimage02}, #{thumb_boimage03}, 0, 0)")
    int Insert(final BoardDTO boardDto);

    // 게시물 상세보기
    @Select("SELECT b.bono, b.memNo, b.botitle, b.bocontent, b.BO_CREATE_AT, b.boimage01, b.boimage02, b.boimage03, b.thumb_boimage01, b.thumb_boimage02, b.thumb_boimage03, " +
            "m.memberNick, " +
            "COALESCE(c.ascommentCount, 0) AS commentCount, b.viewCount, b.likeCount " +
            "FROM boards b " +
            "JOIN tmem m ON b.memNo = m.memNo " +
            "LEFT JOIN (SELECT bono, COUNT(*) AS ascommentCount FROM comments GROUP BY bono) c ON b.bono = c.bono " +
            "WHERE b.bono = #{bono}")
    BoardDTO SelectOne(@Param("bono") Integer bono);

    // 게시물 업데이트
    @Update("UPDATE boards SET memNo = #{memNo}, botitle = #{botitle}, bocontent = #{bocontent}, boimage01 = #{boimage01}, boimage02 = #{boimage02}, boimage03 = #{boimage03}, viewCount = #{viewCount}, likeCount = #{likeCount} WHERE bono = #{bono}")
    int Update(@Param("boardDto") BoardDTO boardDto);

    // 게시물 삭제
    @Delete("DELETE FROM boards WHERE bono = #{bono}")
    int Delete(@Param("bono") Integer bono);

    // 조회수 업데이트
    @Update("UPDATE boards SET viewCount = viewCount + 1 WHERE bono = #{bono}")
    int updateViewCount(Integer bono);

    // 좋아요 상태 체크
    @Select("SELECT COUNT(*) FROM likes WHERE BONO = #{bono} AND memNo = #{memNo}")
    int checkLike(@Param("bono") Integer bono, @Param("memNo") Integer memNo);

    // 좋아요 추가
    @Insert("INSERT INTO likes (BONO, memNo) VALUES (#{bono}, #{memNo}) ON DUPLICATE KEY UPDATE LIKENO=LIKENO")
    int addLike(@Param("bono") Integer bono, @Param("memNo") Integer memNo);

    // 좋아요 취소
    @Delete("DELETE FROM likes WHERE BONO = #{bono} AND memNo = #{memNo}")
    int removeLike(@Param("bono") Integer bono, @Param("memNo") Integer memNo);
}
