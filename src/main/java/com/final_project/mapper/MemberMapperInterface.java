package com.final_project.mapper;

import com.final_project.dto.MemberDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapperInterface {
    @Insert("INSERT INTO B_MEMBER (MEMEMAIL, MEMPW, MEMBERNICK, MEMPHONE, MEMADDRESS) " +
            "VALUES (#{memEmail}, #{memPw}, #{memberNick}, #{memPhone}, #{memAddress})")
    void insertMember(MemberDTO member);

    @Select("SELECT COUNT(*) FROM B_MEMBER WHERE MEMEMAIL = #{newEmail}")
    int countByEmail(@Param("newEmail") String newEmail);
}
