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
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGENO")
    private Integer messageNo;

    @Column(name = "ROOMNO")
    private Integer roomNo;

    @Column(name = "SENDERNO")
    private Integer senderNo;

    @Column(name = "MESSAGETEXT")
    private String messageText;

    @Column(name = "MCREATEDAT")
    private Timestamp messageCreatedAt;
}
