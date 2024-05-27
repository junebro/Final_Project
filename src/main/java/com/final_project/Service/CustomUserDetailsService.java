package com.final_project.Service;

import com.final_project.dto.MemberDTO;
import com.final_project.mapper.MemberMapperInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapperInterface userMapper;

    @Override
    public UserDetails loadUserByUsername(String memEmail) throws UsernameNotFoundException {
        MemberDTO member = userMapper.findByEmail(memEmail);
        if (member == null) {
            throw new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다.");
        }
        return createUserDetails(member);
    }

    private UserDetails createUserDetails(MemberDTO member) {
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        // memtype에 따라 권한 부여
        switch (member.getMemtype()) {
            case "01":
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            case "02":
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;
            case "03":
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_GUEST"));
                break;
            default:
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 기본 권한
        }

        log.info("Creating user details for {}, with authorities: {}", member.getUsername(), grantedAuthorities);

        return User.builder()
                .username(member.getUsername())
                .password(member.getMemPw()) // 비밀번호는 이미 인코딩된 상태여야 합니다.
                .authorities(grantedAuthorities)
                .build();
    }
}