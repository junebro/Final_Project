package com.final_project.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Nutrient {
    private double calories;
    private double carbohydrate; // 탄수화물
    private double protein; // 단백질
    private double fat; // 지방
    private double Sodium; // 나트륨
    private double sugar; // 설탕당
    private double cholesterol; // 콜레스테롤
    private double DietaryFiber; // 식이섬유
    private double requiredCalories; // 필요 칼로리
    private double requiredProtein; // 권장 단백질
    private double minProtein; // 평균 필요 단백질
    private double requiredDietaryFiber; // 권장 식이섬유
    private double carbohydrateRate; // 탄수화물비율
    private double proteinRate; // 단백질 비율
    private double fatRate; // 지방 비율

}
