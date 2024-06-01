package com.final_project.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private String ordno; // 주문 ID (예: 주문 번호) (주문 번호 : 고객넘버+날짜+시퀀스)
    private int memno; // 고객 ID (고객 테이블과의 Foreign Key)
    private Double ordpr; // 주문 총액
    private LocalDateTime orddt; // 주문 날짜
    private String ordst; // 주문 상태 (예: "주문 완료", "배송 중", "배송 완료")

    private String ordnm;
    private String ordzc;
    private String ordar;
    private String orddar;
    private String ordph;

    private String ordbynm;
    private String ordbyzc;
    private String ordbyar;
    private String ordbydar;
    private String ordbyph;
}
