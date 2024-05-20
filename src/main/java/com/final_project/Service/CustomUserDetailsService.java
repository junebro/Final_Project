package com.final_project.Service;

import com.final_project.entity.Member;
import com.final_project.mapper.MemberMapperInterface;
//import com.final_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberMapperInterface userMapper;

    @Override
    public UserDetails loadUserByUsername(String memEmail) throws UsernameNotFoundException {
        Member member = userMapper.findByEmail(memEmail);
        if (member == null) {
            throw new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다.");
        }
        return createUserDetails(member);
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getUsername())
                .password(member.getMemPw())
                .roles(member.getRoles().toArray(new String[0]))
                .build();
    }

}