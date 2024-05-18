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
}
