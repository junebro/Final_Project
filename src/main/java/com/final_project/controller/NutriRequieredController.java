package com.final_project.controller;

import com.final_project.entity.Nutri;
import com.final_project.entity.Nutrient;

public class NutriRequieredController {
    public Nutrient requiredNutrient (Nutri nutri, Nutrient nutrient_info){
        Nutrient nutrient = new Nutrient();
        int age = Integer.parseInt(nutri.getNutriAge());
        if (nutri.getNutriGenderState().equals("1")) {
            // 남성 계산
            nutrient.setRequiredDietaryFiber(25);
            if (age <= 11) {
                nutrient.setRequiredProtein(45);
                nutrient.setMinProtein(35);
            } else if (age <= 14) {
                nutrient.setRequiredProtein(55);
                nutrient.setMinProtein(45);
            } else if (age <= 18) {
                nutrient.setRequiredProtein(65);
                nutrient.setMinProtein(50);
            } else if (age <= 29) {
                nutrient.setRequiredProtein(60);
                nutrient.setMinProtein(50);
            } else if (age <= 49) {
                nutrient.setRequiredProtein(65);
                nutrient.setMinProtein(50);
            } else if (age <= 64) {
                nutrient.setRequiredProtein(60);
                nutrient.setMinProtein(50);
            } else {
                nutrient.setRequiredProtein(55);
                nutrient.setMinProtein(45);
            }
        } else {
            // 여성 계산
            nutrient.setRequiredDietaryFiber(20);
            if (age <= 11) {
                nutrient.setRequiredProtein(40);
                nutrient.setMinProtein(35);
            } else if (age <= 14) {
                nutrient.setRequiredProtein(50);
                nutrient.setMinProtein(45);
            } else if (age <= 18) {
                nutrient.setRequiredProtein(55);
                nutrient.setMinProtein(45);
            } else if (age <= 29) {
                nutrient.setRequiredProtein(50);
                nutrient.setMinProtein(45);
            } else if (age <= 49) {
                nutrient.setRequiredProtein(55);
                nutrient.setMinProtein(45);
            } else if (age <= 64) {
                nutrient.setRequiredProtein(50);
                nutrient.setMinProtein(40);
            } else {
                nutrient.setRequiredProtein(45);
                nutrient.setMinProtein(40);
            }
        }
        // 탄단지 에너지 적정 비율 계산
        double carbohydrateRate = (nutrient_info.getSugar()+nutrient_info.getCarbohydrate()) * 4 /nutrient_info.getCalories() * 100;
        double proteinRate = nutrient_info.getProtein() * 4 / nutrient_info.getCalories() * 100;
        double fatRate = nutrient_info.getFat() * 9 / nutrient_info.getCalories() * 100;

        nutrient.setCarbohydrateRate(carbohydrateRate);
        nutrient.setProteinRate(proteinRate);
        nutrient.setFatRate(fatRate);

        return nutrient;
    }

}
