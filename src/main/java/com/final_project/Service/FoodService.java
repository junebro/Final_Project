package com.final_project.Service;

import com.final_project.dto.FoodDTO;
import com.final_project.dto.NutrientDTO;
import com.final_project.dto.ProductsDTO;
import com.final_project.mapper.FoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FoodService {
    @Autowired
    private FoodMapper foodMapper;
    public List<List<FoodDTO>> generateDailyMealPlan() {
        List<List<FoodDTO>> dailyMealPlans = new ArrayList<>();

        // 식사별 칼로리 범위 설정
        int[][] calorieRanges = {
                {250, 450}, // 아침 식사 칼로리 범위
                {450, 550}, // 점심 식사 칼로리 범위
                {550, 650}  // 저녁 식사 칼로리 범위
        };

        Set<String> selectedIds = new HashSet<>(); // 중복을 방지하기 위한 세트

        for (int i = 0; i < 6; i++) { // 6일간의 식사 계획을 생성
            List<FoodDTO> dailyMeals = new ArrayList<>();

            for (int[] range : calorieRanges) {
                int minCalories = range[0];
                int maxCalories = range[1];
                FoodDTO food;
                do {
                    food = foodMapper.findFoodByCalorieRange(minCalories, maxCalories, selectedIds);
                } while (food != null && !selectedIds.add(food.getProcd())); // 이미 선택된 ID가 아닐 때까지 반복
                if (food != null) {
                    dailyMeals.add(food);
                }
            }

            dailyMealPlans.add(dailyMeals); // 하루치 식사 계획을 추가
        }

        return dailyMealPlans;
    }

    public NutrientDTO memSelect(String userNo) {
        return foodMapper.memSelect(userNo);
    }
}
