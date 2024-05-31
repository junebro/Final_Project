package com.final_project.mapper;

import com.final_project.dto.MypageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MypageMapperInterface {
    // 로그인 멤버의 작성글 조회
    @Select(value = "SELECT * FROM BOARDS b left outer join tmem m on b.mbrno = m.memno WHERE MBRNO = ${userNo};")
    List<MypageDTO> SelectAll(@Param("userNo")String userNo);

    // 좋아요한 글 조회
    @Select(value = "SELECT * FROM BOARDS b\n" +
            "LEFT OUTER JOIN tmem m ON b.mbrno = m.memno\n" +
            "LEFT OUTER JOIN likes l ON b.bono = l.bono\n" +
            "WHERE b.MBRNO = ${userNo};")
    List<MypageDTO> SelectLike(@Param("userNo")String userNo);
}