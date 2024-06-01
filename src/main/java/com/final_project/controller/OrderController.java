package com.final_project.controller;

import com.final_project.Service.OrderService;
import com.final_project.dto.CompleteOrderRequest;
import com.final_project.dto.MemberDTO;
import com.final_project.dto.OrderDTO;
import com.final_project.dto.OrderDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService os;
    @PostMapping("/insertorder")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
        OrderDTO createdOrder = os.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/selectorder/{userNo}")
    public ResponseEntity<MemberDTO> selectMember(@PathVariable int userNo, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        System.out.println("Received token: " + token); // 토큰 로깅

        MemberDTO selectMember = os.selectMember(userNo);
        return ResponseEntity.ok(selectMember);
    }


    @PostMapping("/complete")
    public ResponseEntity<?> completeOrder(@RequestBody CompleteOrderRequest completeOrderRequest) {

        System.out.println("121231231");
        String paymentKey = completeOrderRequest.getPaymentKey();
        List<OrderDetailDTO> detailData = completeOrderRequest.getDetailData();
        System.out.println("11111111111111111111");
        System.out.println(completeOrderRequest.getMemno());

        int memno = completeOrderRequest.getMemno();

        // 결제 완료 처리 로직 구현
        boolean isSuccess = os.completeOrder(paymentKey, detailData);
        boolean isDelete = os.deleteCart(memno);

        if (isSuccess) {
            return ResponseEntity.ok("Payment and order completion successful");
        } else {
            return ResponseEntity.status(400).body("Payment and order completion failed");
        }
    }
}
