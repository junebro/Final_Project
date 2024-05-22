package com.final_project.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Comment {
    private Integer comno;

    private Integer bono;

    private Integer mbrno;

    private String comContent;
}
