package com.final_project.final_project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.*;
@Entity
@Getter @Setter
public class Item {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String  id = "abc";
    private String name = "가나다";
    private String description = "12345667878";

    // getters and setters
}