package com.final_project.Service;

import com.final_project.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService implements UserDetailsService {

    private final MemberService.LoginService loginService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String insertedUserId) throws UsernameNotFoundException {
        Optional<Member> findOne = loginService.findOne(insertedUserId);
        log.info("findOne : {}", findOne);
        Member newMember = findOne.orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 ID입니다."));

        // 사용자 비밀번호가 암호화되어 저장되었는지 확인
        return User.builder()
                .username(newMember.getMemEmail())
                .password(newMember.getMemPw()) // 데이터베이스에 저장된 암호화된 비밀번호
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .passwordEncoder(passwordEncoder::encode) // 로그인 시 입력된 비밀번호 암호화
                .build();
    }

    public Member createUser(String userId, String pw, PasswordEncoder passwordEncoder) {
        Member newMember = new Member();
        newMember.setMemNo(1);  // ID 설정 방법을 검토해야 할 수 있습니다.
        newMember.setMemEmail(userId);
        newMember.setMemPw(passwordEncoder.encode(pw));
        return newMember;
    }

}