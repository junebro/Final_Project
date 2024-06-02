package com.final_project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Getter
@Setter
@ToString
public class MemberDTO implements UserDetails {
    private String memtype;
    private String memNo;
    private String memEmail;
    private String memPw;
    private String memberNick;
    private String memAddress;
    private String detailAddress;
    private String zonecode;
    private int disNo;
    private String gender;
    private double memheight;
    private double memweight;
    private int memage;
    private String memImage; // 이미지 수정

    // UserDetails 메서드 구현

//    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
//    private List<String> roles = new ArrayList<>();

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(memtype));
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

    public void setUserNo(Long userNo) {
    }

//
//    public MemberDTO toMember() {
//        MemberDTO member = new MemberDTO();
//        member.setMemEmail(this.memEmail);
//        member.setMemPw(this.memPw);
//        member.setMemberNick(this.memberNick);
//        member.setMemAddress(this.memAddress);
//        member.setDetailAddress(this.detailAddress);
//        member.setZonecode(this.zonecode);
//        member.setDisNo(this.disNo);
//        return member;
//    }
}
