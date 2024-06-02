package com.final_project.Service;
import com.final_project.dto.MemberDTO;
import com.final_project.mapper.MemberMapperInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class MemberService {
    private final MemberMapperInterface memberMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberMapperInterface memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    @Transactional
    public void registerNewMember(MemberDTO memberDTO) {

        String encodedPassword = passwordEncoder.encode(memberDTO.getMemPw());

        MemberDTO member = new MemberDTO();
        member.setMemEmail(memberDTO.getMemEmail());
        member.setMemPw(encodedPassword); // 암호화된 비밀번호 저장
        member.setMemtype(memberDTO.getMemtype());
        member.setMemberNick(memberDTO.getMemberNick());
        member.setMemAddress(memberDTO.getMemAddress());
        member.setDetailAddress(memberDTO.getDetailAddress());
        member.setZonecode(memberDTO.getZonecode());
        member.setGender(memberDTO.getGender());
        member.setMemage(memberDTO.getMemage());
        member.setMemheight(memberDTO.getMemheight());
        member.setMemweight(memberDTO.getMemweight());
        memberMapper.insertMember(member);
    }

    // 이메일 중복 체크
    public boolean isEmailAvailable(String newEmail) {
        // 이메일이 이미 DB에 존재하는지 확인
        return memberMapper.countByEmail(newEmail) == 0;
    }

    // 닉네임 중복 체크
    public boolean isNickAvailable(String newNick) {
        // 닉네임이 이미 DB에 존재하는지 확인
        return memberMapper.countByNick(newNick) == 0;
    }

    // 내 정보 조회
    public MemberDTO SelectMem(String userNo) {
        return memberMapper.SelectMem(userNo);
    }

    // 내 정보 수정
    public void Update(MemberDTO member) {memberMapper.UpdateMem(member);}


    public void DeleteMem(String userNo) {
        memberMapper.DeleteMem(userNo);
    }

    public void updateUserProfileImage(String userNo, String imagePath) {
        memberMapper.updateProfileImagePath(userNo, imagePath);
    }
}