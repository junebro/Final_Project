package com.final_project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageDTO {
    private String messagetext; // 메시지 내용
    private int senderMemNo;    // 발신자 회원 번호
}
