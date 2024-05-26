package com.final_project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    private String orderId;     // 주문을 식별하기 위한 고유한 ID (고객넘버+날짜+시퀀스)
    private Long id;      // 각 결제 레코드를 고유하게 식별하기 위한 기본 키
    private String customerName;    // 고객 아이디
    private Double amount;  // 결제 금액
    private String status;  // 결제 상태 (예: 성공, 실패)
}
