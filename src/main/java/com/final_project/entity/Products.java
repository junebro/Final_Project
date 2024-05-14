package com.final_project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Products {
    @Id
    private String protp;
    private String prostp;
    private String procd;
    private String pronm;
    private double propr;
    private String proimg;
}
