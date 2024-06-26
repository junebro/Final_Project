package com.final_project.controller;

import com.final_project.Service.CartService;
import com.final_project.dto.CartDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // 이 부분을 @RestController로 변경
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final CartService cs;

    @GetMapping(value = "/cartselect/{userNo}")
    public ResponseEntity<List<CartDTO>> SelectAll(@PathVariable int userNo) {
        List<CartDTO> cartList = cs.Select(userNo);

        return ResponseEntity.ok(cartList);
    }

    @PostMapping("/cartinsert")
    public ResponseEntity<?> createCart(@RequestBody CartDTO cart) {

        try {
            // 비즈니스 로직 실행
            int cnt = cs.Insert(cart);
            if (cnt == 1) {
                return ResponseEntity.ok("Insert successful");
            } else {
                throw new Exception("Insert failed");
            }
        } catch (Exception e) {
            logger.error("Error during board insertion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing your request");
        }
    }


    @PostMapping("/cartdelete")
    public ResponseEntity<?> deleteCart(@RequestBody CartDTO cart) {
        try {
            // 비즈니스 로직 실행
            int cnt = cs.Delete(cart);
            System.out.println(cnt);
            if (cnt == 1) {
                return ResponseEntity.ok("Delete successful");
            } else {
                throw new Exception("Delete failed");
            }
        } catch (Exception e) {
            logger.error("Error during board insertion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing your request");
        }
    }

}