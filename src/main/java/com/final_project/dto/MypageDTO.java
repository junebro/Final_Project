package com.final_project.dto;

import lombok.Data;
import org.springframework.security.core.parameters.P;

@Data
public class MypageDTO {
    private int bono;
    private int mbrno;
    private String botitle;
    private int viewCount;
    private String bo_create_at;
    private String memberNick;
    private int likeno;
}
