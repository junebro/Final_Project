package com.final_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {
    private Integer bono;

    private Integer mbrno;

    private String botitle;

    private String bocontent;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone="Asia/Seoul")
    private String BO_CREATE_AT;

    private String boimage01 ;
    private String boimage02 ;
    private String boimage03 ;
    private String thumb_boimage01;
    private String thumb_boimage02;
    private String thumb_boimage03;
}
