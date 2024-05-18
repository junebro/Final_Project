package com.final_project.controller;

import com.final_project.Service.ProductsService;
import com.final_project.dto.ProductsDTO;
import com.final_project.entity.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@RestController  // 이 부분을 @RestController로 변경
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductsController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
    private final ProductsService ps;

    @GetMapping(value = "/products/{userNo}")
    public ResponseEntity<List<Products>> SelectAll(@PathVariable String userNo) {
        List<Products> productList = ps.SelectAll(userNo);
//        List<ProductsDTO> productDataList = productList.stream().map(product -> {
//
//            ProductsDTO data = new ProductsDTO();
//
//            data.setProtp(product.getProtp());
//            data.setProstp(product.getProstp());
//            data.setProcd(product.getProcd());
//            data.setPronm(product.getPronm());
//            data.setPropr(product.getPropr());
//            data.setProimg(product.getProimg());
//
//            data.setPifimg1(product.getPifimg1());
//            data.setPifimg2(product.getPifimg2());
//            data.setPifimg3(product.getPifimg3());
////            if (product.getPifimg1() != null) data.setPifimg1(Base64Utils.encodeToString(product.getPifimg1()));
////            if (product.getPifimg2() != null) data.setPifimg2(Base64Utils.encodeToString(product.getPifimg2()));
////            if (product.getPifimg3() != null) data.setPifimg3(Base64Utils.encodeToString(product.getPifimg3()));
//            data.setPiftt(product.getPiftt());
//            data.setPifcal(product.getPifcal());
//            data.setPifna(product.getPifna());
//            data.setPiftan(product.getPiftan());
//            data.setPifsu(product.getPifsu());
//            data.setPiffat(product.getPiffat());
//            data.setPiftrf(product.getPiftrf());
//            data.setPifsat(product.getPifsat());
//            data.setPifclt(product.getPifclt());
//            data.setPifprt(product.getPifprt());
//            System.out.println(data);
//            return data;
//        }).collect(Collectors.toList());

 //       return ResponseEntity.ok(productDataList);
        return ResponseEntity.ok(productList);
    }
}