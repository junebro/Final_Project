package com.final_project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "B_MEMBER")
public class Member {
    // 인증은 이메일이랑 비번으로 진행할 것
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMNO")
    private int memNo;

    @Column(name = "MEMEMAIL", length = 50)
    private String memEmail;

    @Column(name = "MEMPW", length = 100)
    private String memPw;

    @Column(name = "MEMBERNICK", length = 30)
    private String memberNick;

//    @Column(name = "MEMPHONE", length = 11)
//    private String memPhone;

    @Column(name = "MEMADDRESS", length = 255)
    private String memAddress;

    @Column(name = "DISNO")
    private int disNo;

//    @Column(name = "SNSLOGIN", length = 2)
//    private String snsLogin;
//
//    @Column(name = "ENROLLDATE")
//    private Date enrollDate;
//
//    @Column(name = "SESSION_FL", length = 2)
//    private String sessionFl;

}
