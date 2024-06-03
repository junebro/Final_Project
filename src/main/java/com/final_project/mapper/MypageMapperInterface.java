package com.final_project.mapper;

import com.final_project.dto.MypageDTO;
import com.final_project.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MypageMapperInterface {
    // 로그인 멤버의 작성글 조회
    @Select(value = "SELECT \n" +
            " A.mbrno, A.bono, A.botitle, B.membernick, A.bo_create_at, A.viewCount \n" +
            " FROM boards A\n" +
            " LEFT OUTER JOIN tmem B on A.mbrno = B.memno\n" +
            " WHERE A.mbrno = ${userNo};")
    List<MypageDTO> SelectAll(@Param("userNo")String userNo);

    // 좋아요한 글 조회
    @Select(value = "SELECT \n" +
            " A.mbrno, A.bono, A.botitle, C.membernick, A.bo_create_at, A.viewCount FROM boards A\n" +
            " LEFT OUTER JOIN likes B on A.bono = B.bono\n" +
            " LEFT OUTER JOIN tmem C ON A.mbrno = C.memno\n" +
            " WHERE B.mbrno = ${userNo};")
    List<MypageDTO> SelectLike(@Param("userNo")String userNo);

    // 마이페이지 - 주문내역 조회
    @Select("SELECT A.ordno, A.memno, A.ordpr, A.orddt, B.procd, B.crtqt, C.pronm, C.propr, C.proimg, C.propr\n" +
            "FROM TORD A\n" +
            "LEFT OUTER JOIN tors B ON A.ordno = B.ordno\n" +
            "LEFT OUTER JOIN tpro C ON B.procd = C.procd\n" +
            "WHERE memno = ${userNo};")
    List<OrderDTO> SelectOrderAll(@Param("userNo")String userNo);
}
