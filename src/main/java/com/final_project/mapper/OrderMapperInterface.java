package com.final_project.mapper;

import com.final_project.dto.MemberDTO;
import com.final_project.dto.OrderDTO;
import com.final_project.dto.OrderDetailDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapperInterface {
    @Insert("INSERT INTO TORD (ordno, memno, ordpr, orddt, ordst, ordnm, ordzc, ordar, orddar, ordph, ordbynm, ordbyzc, ordbyar, ordbydar, ordbyph) " +
            "VALUES (#{ordno}, #{memno}, #{ordpr}, #{orddt}, #{ordst}, #{ordnm}, #{ordzc}, #{ordar}, #{orddar}, #{ordph}, #{ordbynm}, #{ordbyzc}, #{ordbyar}, #{ordbydar}, #{ordbyph})")
    void save(OrderDTO order);

    @Insert("INSERT INTO order_sequence () VALUES ()")
    void insertOrderSequence();

    @Select("SELECT LAST_INSERT_ID()")
    Long getNextOrderSequence();

    @Insert("INSERT INTO tors (ordno, procd, crtqt, propr) VALUES (#{ordno}, #{procd}, #{crtqt}, #{propr});")
    void insertOrderDetail(OrderDetailDTO detail);

    @Select("SELECT * FROM TMEM WHERE MEMNO =#{userNo};")
    MemberDTO SelectMember(@Param("userNo") int userNo);

    @Insert("DELETE FROM TCRT WHERE mbrno = #{memno};")
    boolean DeleteCart( int memno);
}
