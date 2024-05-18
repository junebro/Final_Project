package com.final_project.mapper;

import com.final_project.dto.MemberDTO;
import com.final_project.entity.Member;
import com.final_project.entity.Products;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface MemberMapperInterface {
    // 회원가입
    @Insert("INSERT INTO B_MEMBER (MEMEMAIL, MEMPW, MEMBERNICK, MEMADDRESS, DISNO)" +
            "VALUES (#{member.memEmail}, #{member.memPw}, #{member.memberNick}, #{member.memAddress}, 1)")
    void insertMember(@Param("member") Member member);

    // 이메일 중복 검사
    @Select("SELECT COUNT(*) FROM B_MEMBER WHERE MEMEMAIL = #{newEmail}")
    int countByEmail(@Param("newEmail") String newEmail);

    // 닉네임 중복 검사
    @Select("SELECT COUNT(*) FROM B_MEMBER WHERE MEMBERNICK = #{memberNick}")
    int countByNick(@Param("memberNick") String memberNick);

    // 회원 가입 여부 체크
    @Select("SELECT * FROM b_member WHERE mememail = #{email}")
    Member findByEmail(String email);
}
