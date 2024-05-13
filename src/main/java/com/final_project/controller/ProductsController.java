package com.final_project.controller;

import com.final_project.Service.ProductsService;
import com.final_project.entity.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController  // 이 부분을 @RestController로 변경
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductsController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
    private final ProductsService ps;

    @GetMapping(value = "/products")
    public ResponseEntity<List<Products>> SelectAll() {
        List<Products> productList = ps.SelectAll();
        logger.info("Retrieved products: {}", productList);
        return ResponseEntity.ok(productList);
    }
}