package com.final_project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Item {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String  id = "abcdd";
    private String name = "가나다라";
    private String description = "12345667878";

    // getters and setters
}