package com.final_project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Item {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String  id ;
    private String name ;
    private String description ;


    // getters and setters
}