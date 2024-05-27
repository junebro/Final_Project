package com.final_project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter @ToString
public class NutriInfo {
    Map<String, Integer> items = new HashMap<>();

    int drag1_item_1 = 504; // 볶음밥
    int drag1_item_2 = 300; // 밥
    int drag1_item_3 = 266; // 빵류

    int drag2_item_1 = 79; // 두부콩
    int drag2_item_2 = 52; // 과일
    int drag2_item_3 = 122; // 유제품류

    int drag3_item_1 = 56; // 견과류
    int drag3_item_2 = 619; // 패스트푸드류
    int drag3_item_3 = 174; // 달콤한간식

    int drag4_item_1 = 128; // 찌개
    int drag4_item_2 = 385; // 면

    int drag5_item_1 = 7; // 생채소
    int drag5_item_2 = 24; // 채소반찬
    int drag5_item_3 = 21; // 김치절임

    int drag6_item_1 = 661; // 육류
    int drag6_item_2 = 379; // 해산물류
    int drag6_item_3 = 89; // 계란류

    int drag7_item_1 = 92; // 가당음료
    int drag7_item_2 = 385; // 과자
    int drag7_item_3 = 45; // 사탕

    // 생성자에서 items 맵을 초기화
}
