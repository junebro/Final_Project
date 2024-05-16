package com.final_project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class MemberDTO {
    private String memEmail;
    private String memPw;
    private String memberNick;
    private String memPhone;
    private String memAddress;

}
