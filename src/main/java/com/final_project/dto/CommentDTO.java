package com.final_project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Integer comno;

    private Integer bono;

    private String memNo;

    private String comContent;

    private String memberNick;
}
