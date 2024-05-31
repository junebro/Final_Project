package com.final_project.mapper;


import com.final_project.dto.MemberDTO;
import com.final_project.dto.MypageDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface MemberMapperInterface {
    // 회원가입
    @Insert("INSERT INTO TMEM (MEMTYPE, MEMEMAIL, MEMPW, MEMBERNICK, MEMADDRESS, DETAILADDRESS, ZONECODE, DISNO, GENDER, MEMHEIGHT, MEMWEIGHT, MEMAGE)" +
            "VALUES (#{member.memtype}, #{member.memEmail}, #{member.memPw}, #{member.memberNick}, #{member.memAddress}, #{member.detailAddress}, #{member.zonecode}, 1, #{member.gender}, #{member.memheight}, #{member.memweight}, #{member.memage})")
    void insertMember(@Param("member") MemberDTO member);

    // 이메일 중복 검사
    @Select("SELECT COUNT(*) FROM TMEM WHERE MEMEMAIL = #{newEmail}")
    int countByEmail(@Param("newEmail") String newEmail);

    // 닉네임 중복 검사
    @Select("SELECT COUNT(*) FROM TMEM WHERE MEMBERNICK = #{memberNick}")
    int countByNick(@Param("memberNick") String memberNick);

    // 회원 가입 여부 체크
    @Select("SELECT * FROM TMEM WHERE mememail = #{email}")
    MemberDTO findByEmail(String email);

    // 내 정보 조회
    @Select("SELECT * FROM tmem WHERE MEMNO = ${userNo};")
    MemberDTO SelectMem(@Param("userNo")String userNo);
}
