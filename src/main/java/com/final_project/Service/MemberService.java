package com.final_project.Service;

import com.final_project.dto.MemberDTO;
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

    @Transactional
    public void registerNewMember(MemberDTO memberDTO) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberDTO.getMemPw());
        memberDTO.setMemPw(encodedPassword);

        // 회원 정보 DB에 저장
        memberMapper.insertMember(memberDTO);
    }

    // 이메일 중복 체크
    public boolean isEmailAvailable(String newEmail) {
        // 이메일이 이미 DB에 존재하는지 확인
        return memberMapper.countByEmail(newEmail) == 0;
    }

    @Service
    @RequiredArgsConstructor
    public class LoginService {

        private final MemberRepository memberRepository;

        public Optional<Member> findOne(String mememail) {
            return memberRepository.findByUserId(mememail);
        }

    }
}
