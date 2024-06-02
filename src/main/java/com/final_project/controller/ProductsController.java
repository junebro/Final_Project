package com.final_project.controller;

import com.final_project.Service.ProductsService;
import com.final_project.dto.ProductsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController  // 이 부분을 @RestController로 변경
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductsController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
    private final ProductsService ps;

    @GetMapping(value = "/products/{userNo}")
    public ResponseEntity<List<ProductsDTO>> selectAll(@PathVariable String userNo, @RequestParam(required = false) Integer protp) {
        List<ProductsDTO> productList = ps.SelectAll(userNo, protp);
        return ResponseEntity.ok(productList);
    }
}