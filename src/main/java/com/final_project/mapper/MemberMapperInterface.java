package com.final_project.mapper;


import com.final_project.dto.MemberDTO;
import org.apache.ibatis.annotations.*;


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
    @Select("SELECT * FROM TMEM WHERE mememail = #{email} AND session_fl IS NULL ")
    MemberDTO findByEmail(String email);

    // 내 정보 조회
    @Select("SELECT * FROM tmem WHERE MEMNO = ${userNo};")
    MemberDTO SelectMem(@Param("userNo")String userNo);

    // 마이페이지 업데이트
    @Update("UPDATE tmem SET memImage = #{memImage}, MEMBERNICK = #{memberNick}, GENDER = #{gender}, MEMAGE = #{memage}, MEMHEIGHT = #{memheight}, MEMWEIGHT = #{memweight}, ZONECODE = #{zonecode}, MEMADDRESS = #{memAddress}, DETAILADDRESS = #{detailAddress} where memno=${memNo};")
    void UpdateMem(MemberDTO member);

    // 회원 탈퇴
    @Delete("DELETE FROM tmem WHERE memno = ${userNo};")
    void DeleteMem(@Param("userNo")String userNo);

    @Update("UPDATE tmem SET memimage = #{imagePath} WHERE memno = #{userNo}")
    void updateProfileImagePath(@Param("userNo") String userNo, @Param("imagePath") String imagePath);

    // 이메일을 통해 회원 번호(memNo)를 찾는 쿼리
    @Select("SELECT memNo FROM tmem WHERE memEmail = #{email}")
    Integer findMemNoByEmail(String email);

    @Select("SELECT memNo FROM tmem WHERE memNo = #{userId}")
    Integer findMemNoByUserId(String userId);

    @Select("SELECT memberNick FROM tmem WHERE memNo = #{userId}")
    String findMemberNickByUserId(String userId);
}
