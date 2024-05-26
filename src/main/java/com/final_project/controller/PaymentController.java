//package com.final_project.controller;
//// PaymentController.java
//
//import com.final_project.Service.PaymentService;
//import com.final_project.entity.Payment;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/payments")
//public class PaymentController {
//
//    @Autowired
//     private PaymentService paymentService;
//
//    @PostMapping("/callback")
//    public ResponseEntity<String> handlePaymentCallback(@RequestBody Payment paymentData) {
//        try {
//            if ("SUCCESS".equals(paymentData.getStatus())) {
//                paymentService.savePayment(paymentData);
////                paymentService.updateOrderStatus(paymentData.getOrderId(), "paid");
//                paymentService.sendConfirmationEmail(paymentData.getCustomerName(), paymentData);
//                return ResponseEntity.ok("Payment processed successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
//        }
//    }
//}