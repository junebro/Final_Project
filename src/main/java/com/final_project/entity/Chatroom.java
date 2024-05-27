package com.final_project.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity
@Table(name = "chatrooms")
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOMNO")
    private Integer roomNo;

    @Column(name = "MEMBERID")
    private Integer memberId;

    @Column(name = "ADMINID")
    private Integer adminId;

    @Column(name = "ROOMCREATEAT")
    private Timestamp createdAt;
}
