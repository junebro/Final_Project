package com.final_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
public class Board {
    private Integer bono;

    private Integer mbrno;

    private String botitle;

    private String bocontent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime BO_CREATE_AT;

}
