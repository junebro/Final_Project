package com.final_project.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "B_MEMBER")
public class Member implements UserDetails {


    // 인증은 이메일이랑 비번으로 진행할 것
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMNO")
    private String memNo;

    @Column(name = "MEMEMAIL", length = 50)
    private String memEmail;

    @Column(name = "MEMPW", length = 100)
    private String memPw;

    @Column(name = "MEMBERNICK", length = 30)
    private String memberNick;

    @Column(name = "MEMADDRESS", length = 255)
    private String memAddress;

    @Column(name = "DISNO")
    private int disNo;

    // UserDetails 메서드 구현
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return memPw;
    }

    @Override
    public String getUsername() {
        return memNo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
