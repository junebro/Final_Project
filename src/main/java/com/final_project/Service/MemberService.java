package com.final_project.Service;

import com.final_project.dto.MemberDTO;
import com.final_project.entity.Board;
import com.final_project.entity.Member;
import com.final_project.mapper.MemberMapperInterface;
import com.final_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberMapperInterface memberMapper;

    @Autowired
    public MemberService(MemberMapperInterface memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public void registerNewMember(MemberDTO memberDTO) {

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberDTO.getMemPw());
        memberDTO.setMemPw(encodedPassword);
        memberDTO.setDisNo(1);
        // MemberDTO를 Member로 변환하여 매퍼에 전달
        memberMapper.insertMember(memberDTO);
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

    @Service
    @RequiredArgsConstructor
    public class LoginService {

        private final MemberRepository memberRepository;
        // 준형 수정 findByMemEmail
        public Optional<Member> findOne(String mememail) {
            return memberRepository.findByMemEmail(mememail);
        }
    }

    // 회원가입
  
}
