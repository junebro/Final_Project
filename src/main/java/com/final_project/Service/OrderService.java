package com.final_project.Service;

import com.final_project.dto.OrderDTO;
import com.final_project.mapper.OrderMapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderService {
    @Autowired
    private OrderMapperInterface omif;
    public OrderDTO createOrder(OrderDTO order) {

        // 고객 번호 가져오기
        int memno = order.getMemno();

        // 현재 날짜 가져오기
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 시퀀스 번호 가져오기
        Long sequence = omif.getNextOrderSequence();

        // 주문 번호 생성 (고객 번호 + 날짜 + 시퀀스)
        String orderId = memno + currentDate + sequence;
        order.setOrdno(orderId);

        // 주문 날짜 설정
        order.setOrddt(LocalDateTime.now());

        // 주문 저장
        omif.save(order);

        return order;

        //return omif.save(order);
    }
}
