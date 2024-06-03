package com.final_project.mapper;

import com.final_project.dto.CartDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CartMapperInterface {

    @Select("SELECT \n" +
                    " A.mbrno, A.crtcd, A.crtqt, B.prostp, B.pronm, B.propr, B.proimg, C.pifimg1, C.pifimg2, C.pifimg3\n" +
                    " FROM \n" +
                    " tcrt A \n" +
                    " LEFT OUTER JOIN tpro B ON A.crtcd = B.procd\n" +
                    " LEFT OUTER JOIN tpif C ON B.procd = C.procd\n" +
                    " WHERE A.mbrno = #{userNo};")
    List<CartDTO> Select(@Param("userNo") int userNo);

    @Insert("INSERT INTO tcrt (mbrno, crtcd, crtqt) VALUES (#{cart.mbrno}, #{cart.crtcd}, #{cart.crtqt})")
    int Insert(@Param("cart") CartDTO cart);

    @Insert("DELETE FROM tcrt WHERE mbrno = #{cart.mbrno} AND crtcd = #{cart.crtcd}")
    int Delete(@Param("cart") CartDTO cart);

    /* 식단 추천에서 저장시킴 */
    @Insert({
            "<script>",
            "INSERT INTO tcrt (mbrno, crtcd, crtqt) VALUES ",
            "<foreach collection='cartItems' item='item' index='index' separator=','>",
            "(#{item.mbrno}, #{item.crtcd}, #{item.crtqt})",
            "</foreach>",
            "</script>"
    })
    void insertCartItems(@Param("cartItems") List<CartDTO> cartItems);
}