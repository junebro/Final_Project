package com.final_project.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProductsDTO {
    private String protp;
    private String prostp;
    private String procd;
    private String pronm;
    private double propr;
    private String proimg;

    // 상품 상세 영역
    // 나중에 수정해야됨
    private String pifimg1;
    private String pifimg2;
    private String pifimg3;
//    @Lob
//    private byte[] pifimg1;
//    @Lob
//    private byte[]  pifimg2;
//    @Lob
//    private byte[] pifimg3;

    private double piftt;
    private double pifcal;
    private double pifna;
    private double piftan;
    private double pifsu;
    private double piffat;
    private double piftrf;
    private double pifsat;
    private double pifclt;
    private double pifprt;

    // 장바구니 체크
    private String crtck;
}
