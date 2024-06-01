package com.final_project.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDetailDTO {
    private int orsno;
    private String ordno;
    private String procd;
    private String crtqt;
    private String propr;
}

