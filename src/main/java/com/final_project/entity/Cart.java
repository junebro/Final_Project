package com.final_project.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    private int  mbrno ;
    private String crtcd ;
    private int crtqt ;
    private String prostp;
    private String pronm;
    private double propr;
    private String proimg;
    private String pifimg1;
    private String pifimg2;
    private String pifimg3;
}
