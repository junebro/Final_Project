package com.final_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompleteOrderRequest {
    private String paymentKey;
    private int memno;
    private List<OrderDetailDTO> detailData;

}
