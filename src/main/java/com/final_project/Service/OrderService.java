package com.final_project.Service;

import com.final_project.dto.MemberDTO;
import com.final_project.dto.OrderDTO;
import com.final_project.dto.OrderDetailDTO;
import com.final_project.mapper.OrderMapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapperInterface omi;

    public OrderDTO createOrder(OrderDTO order) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(order);
        // 고객 번호 가져오기
        int memno = order.getMemno();
        // 현재 날짜 가져오기
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 시퀀스 번호 삽입
        omi.insertOrderSequence();
        // 시퀀스 번호 가져오기
        Long sequence = omi.getNextOrderSequence();

        // 주문 번호 생성 (고객 번호 + 날짜 + 시퀀스)
        String orderId = memno + currentDate + sequence;
        order.setOrdno(orderId);
        // 주문 날짜 설정
        order.setOrddt(LocalDateTime.now());
        // 주문 저장
        omi.save(order);
        return order;
    }

    public boolean completeOrder(String paymentKey, List<OrderDetailDTO> detailData) {
        // 결제 검증 로직 추가
        // 예: paymentKey를 사용하여 결제 검증

        // 주문 상세 정보 저장
        for (OrderDetailDTO detail : detailData) {
            omi.insertOrderDetail(detail);
        }

        return true;
    }

    public MemberDTO selectMember(int userNo)  {
        return omi.SelectMember(userNo);
    }

    public boolean deleteCart(int memno)  {
        System.out.println(memno);
        return omi.DeleteCart(memno);
    }

}
