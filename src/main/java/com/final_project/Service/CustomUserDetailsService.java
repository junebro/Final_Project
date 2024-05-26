package com.final_project.Service;

import com.final_project.entity.Member;
import com.final_project.mapper.MemberMapperInterface;
//import com.final_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberMapperInterface userMapper;

    @Override
    public UserDetails loadUserByUsername(String memEmail) throws UsernameNotFoundException {
        Member member = userMapper.findByEmail(memEmail);
        if (member == null) {
            log.error("No member found with email: {}", memEmail);
            throw new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다.");
        }

        log.info("Member roles for {}: {}", memEmail, member.getRoles());
        return createUserDetails(member);
    }

    private UserDetails createUserDetails(Member member) {

        Collection<String> roles = member.getRoles();

        if (roles == null || roles.isEmpty()) {
            log.warn("No roles found for member with username: {}", member.getUsername());
            roles = Collections.singletonList("USER"); // 일반 사용자 로그인
        }

        // mbrtp 값이 1인 경우, 관리자 권한 추가
//        if ("1".equals(member.getMbrtp())) {
//            roles.add("ROLE_ADMIN");
//        }

        return User.builder()
                .username(member.getUsername())
                .password(member.getMemPw())
                .roles(roles.toArray(new String[0]))
                .build();
    }
}