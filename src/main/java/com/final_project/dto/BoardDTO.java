package com.final_project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {
    private Integer bono;
    private String memNo;
    private String botitle;
    private String bocontent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private String BO_CREATE_AT;
    private String boimage01;
    private String boimage02;
    private String boimage03;
    private String thumb_boimage01;
    private String thumb_boimage02;
    private String thumb_boimage03;
    private Integer commentCount; // 댓글 수
    private Integer likeCount; // 좋아요 수
    private Integer viewCount; // 조회수

    private String memberNick; // 작성자 닉네임 추가
}
