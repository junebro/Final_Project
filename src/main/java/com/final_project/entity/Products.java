package com.final_project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Products {
    @Id
    private int product_id;
    private String product_name;
    private double price;
}
