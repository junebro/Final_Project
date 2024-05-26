//package com.final_project.Service;
//
//// PaymentService.java
//import com.final_project.entity.Payment;
//import com.final_project.mapper.PaymentMapperInterface;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PaymentService {
//
//    @Autowired
//    private PaymentMapperInterface paymentMapper;
//
//    public void savePayment(Payment payment) {
//        paymentMapper.insertPayment(payment);
//    }
//
////    public void updateOrderStatus(String orderId, String status) {
////        paymentMapper.updateOrderStatus(orderId, status);
////    }
//
//    public void sendConfirmationEmail(String email, Payment payment) {
//        // 이메일 발송 로직 구현
//    }
//}