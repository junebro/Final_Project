package com.final_project.dto;

import com.final_project.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class MemberDTO {
    private Integer memNo;
    private String memEmail;
    private String memPw;
    private String memberNick;
    private String memPhone;
    private String memAddress;
    private Integer disNo;


    public Member toMember() {
        Member member = new Member();
        member.setMemEmail(this.memEmail);
        member.setMemPw(this.memPw);
        member.setMemberNick(this.memberNick);
        member.setMemAddress(this.memAddress);
        member.setDisNo(this.disNo);
        return member;
    }
}
