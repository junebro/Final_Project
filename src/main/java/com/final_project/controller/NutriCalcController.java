package com.final_project.controller;
import com.final_project.dto.NutriInfo;
import com.final_project.entity.Nutri;
import com.final_project.entity.Nutrient;

import java.util.List;
import java.util.Map;
public class NutriCalcController {

    // 칼로리 계산기
    public Nutrient CalorieCalculator(Map<String, Map<String, List<String>>> drop) {
        NutriInfo bean = new NutriInfo();
        Nutrient N_bean = new Nutrient();
        double cal = 0;
        double carbohydrate=0; // 탄수화물
        double protein=0; // 단백질
        double fat=0; // 지방
        double Sodium=0; // 나트륨
        double sugar=0; // 설탕당
        double cholesterol=0; // 콜레스테롤
        double DietaryFiber=0; // 식이섬유
        // 식사 목록과 섭취량 목록 배열
        String[] dragItems = {"drag1Items", "drag2Items", "drag3Items", "drag4Items", "drag5Items", "drag6Items", "drag7Items"};
        String[] dropItems = {"drop2", "drop3", "drop4", "drop5", "drop6", "drop7"};

        // 목록을 순회하며 해당 되는 항목을 계산해서 칼로리와 영양소의 합을 구함
        for (String dragItem : dragItems) {
            for (String dropItem : dropItems) {
                if (drop.containsKey(dragItem) && drop.get(dragItem).containsKey(dropItem)) {
                    double multiplier = getMultiplier(dropItem); // Get multiplier based on drop item
                    for (String item : drop.get(dragItem).get(dropItem)) {
                        cal += calculateCalories(bean, dragItem, item, multiplier).getCalories();
                        carbohydrate += calculateCalories(bean, dragItem, item, multiplier).getCarbohydrate();
                        protein += calculateCalories(bean, dragItem, item, multiplier).getProtein();
                        fat += calculateCalories(bean, dragItem, item, multiplier).getFat();
                        Sodium += calculateCalories(bean, dragItem, item, multiplier).getSodium();
                        sugar += calculateCalories(bean, dragItem, item, multiplier).getSugar();
                        cholesterol += calculateCalories(bean, dragItem, item, multiplier).getCholesterol();
                        DietaryFiber += calculateCalories(bean, dragItem, item, multiplier).getDietaryFiber();
                    }
                }
            }
        }
        N_bean.setCalories(cal);
        N_bean.setCarbohydrate(carbohydrate);
        N_bean.setProtein(protein);
        N_bean.setFat(fat);
        N_bean.setSodium(Sodium);
        N_bean.setSugar(sugar);
        N_bean.setCholesterol(cholesterol);
        N_bean.setDietaryFiber(DietaryFiber);
        return N_bean;
    }
    // 섭취량에 따른 칼로리 산정 계수
    private double getMultiplier(String dropItem) {
        switch (dropItem) {
            case "drop2": // 거의 먹지 않음
                return 0;
            case "drop3": // 일주일에 1~2번
                return 1.5;
            case "drop4": // 일주일에 3~4번
                return 3.5;
            case "drop5": // 일주일에 5~6번
                return 5.5;
            case "drop6": // 하루에 한번
                return 7;
            case "drop7": // 하루에 두번 이상
                return 14;
            default:
                return 0;
        }
    }
    // 총 칼로리 / 영양성분 계산 영역
    private Nutrient calculateCalories(NutriInfo bean, String dragItem, String item, double multiplier) {
        Nutrient N_bean = new Nutrient();
        switch (dragItem) {
            case "drag1Items":
                switch (item) {
                    case "item-1": // 볶음밥
                        N_bean.setCalories(bean.getDrag1_item_1() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(63.2 * multiplier); // 탄수화물
                        N_bean.setProtein(19 * multiplier);  // 단백질
                        N_bean.setFat(18.7 * multiplier);  // 지방
                        N_bean.setCholesterol(0.156 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(2.1 * multiplier);  // 식이섬유
                        N_bean.setSodium(1.245 * multiplier); // 나트륨
                        N_bean.setSugar(2.28 * multiplier);  // 설탕당
                        break;
                    case "item-2": // 밥
                        N_bean.setCalories(bean.getDrag1_item_2() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(65.1 * multiplier); // 탄수화물
                        N_bean.setProtein(5.7 * multiplier);  // 단백질
                        N_bean.setFat(1 * multiplier);  // 지방
                        N_bean.setCholesterol(0 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(0.8 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.005 * multiplier); // 나트륨
                        N_bean.setSugar(0 * multiplier);  // 설탕당
                        break;
                    case "item-3": // 빵
                        N_bean.setCalories(bean.getDrag1_item_3() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(50.6 * multiplier); // 탄수화물
                        N_bean.setProtein(7.6 * multiplier);  // 단백질
                        N_bean.setFat(3.2 * multiplier);  // 지방
                        N_bean.setCholesterol(0 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(2.4 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.681 * multiplier); // 나트륨
                        N_bean.setSugar(4.3 * multiplier);  // 설탕당
                        break;
                    default:
                        break;
                }
                break;
            case "drag2Items":
                switch (item) {
                    case "item-1": // 두부콩류
                        N_bean.setCalories(bean.getDrag2_item_1() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(2.8 * multiplier); // 탄수화물
                        N_bean.setProtein(8.1 * multiplier);  // 단백질
                        N_bean.setFat(4.2 * multiplier);  // 지방
                        N_bean.setCholesterol(0 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(0.2 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.029 * multiplier); // 나트륨
                        N_bean.setSugar(1 * multiplier);  // 설탕당
                        break;
                    case "item-2": // 과일
                        N_bean.setCalories(bean.getDrag2_item_2() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(13.8 * multiplier); // 탄수화물
                        N_bean.setProtein(0.2 * multiplier);  // 단백질
                        N_bean.setFat(0.1 * multiplier);  // 지방
                        N_bean.setCholesterol(0 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(2.4 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.001 * multiplier); // 나트륨
                        N_bean.setSugar(10.3 * multiplier);  // 설탕당
                        break;
                    case "item-3": // 유제품
                        N_bean.setCalories(bean.getDrag2_item_3() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(11.5 * multiplier); // 탄수화물
                        N_bean.setProtein(8 * multiplier);  // 단백질
                        N_bean.setFat(4.8 * multiplier);  // 지방
                        N_bean.setCholesterol(0.017 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(0 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.1 * multiplier); // 나트륨
                        N_bean.setSugar(12.5 * multiplier);  // 설탕당
                        break;
                    default:
                        break;
                }
                break;
            case "drag3Items":
                switch (item) {
                    case "item-1": // 견과류
                        N_bean.setCalories(bean.getDrag3_item_1() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(1.6 * multiplier); // 탄수화물
                        N_bean.setProtein(2.5 * multiplier);  // 단백질
                        N_bean.setFat(4.9 * multiplier);  // 지방
                        N_bean.setCholesterol(0 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(0.8 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.001 * multiplier); // 나트륨
                        N_bean.setSugar(0.4 * multiplier);  // 설탕당
                        break;
                    case "item-2": // 패스트푸드류
                        N_bean.setCalories(bean.getDrag3_item_2() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(48 * multiplier); // 탄수화물
                        N_bean.setProtein(29 * multiplier);  // 단백질
                        N_bean.setFat(0.34 * multiplier);  // 지방
                        N_bean.setCholesterol(0.08 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(2.8 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.8 * multiplier); // 나트륨
                        N_bean.setSugar(10.5 * multiplier);  // 설탕당
                        break;
                    case "item-3": // 달콤한 간식류
                        N_bean.setCalories(bean.getDrag3_item_3() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(29.6 * multiplier); // 탄수화물
                        N_bean.setProtein(1.6 * multiplier);  // 단백질
                        N_bean.setFat(5.8 * multiplier);  // 지방
                        N_bean.setCholesterol(0.02 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(1 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.01 * multiplier); // 나트륨
                        N_bean.setSugar(24.9 * multiplier);  // 설탕당
                        break;
                    default:
                        break;
                }
                break;
            case "drag4Items":
                switch (item) {
                    case "item-1": // 찌개
                        N_bean.setCalories(bean.getDrag4_item_1() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(6.3 * multiplier); // 탄수화물
                        N_bean.setProtein(8.1 * multiplier);  // 단백질
                        N_bean.setFat(8 * multiplier);  // 지방
                        N_bean.setCholesterol(0.018 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(1.4 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.4 * multiplier); // 나트륨
                        N_bean.setSugar(1.7 * multiplier);  // 설탕당
                        break;
                    case "item-2": // 면
                        N_bean.setCalories(bean.getDrag4_item_2() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(55.6 * multiplier); // 탄수화물
                        N_bean.setProtein(7.9 * multiplier);  // 단백질
                        N_bean.setFat(14.5 * multiplier);  // 지방
                        N_bean.setCholesterol(0 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(2 * multiplier);  // 식이섬유
                        N_bean.setSodium(1 * multiplier); // 나트륨
                        N_bean.setSugar(0.6 * multiplier);  // 설탕당
                        break;
                    default:
                        break;
                }
                break;
            case "drag5Items":
                switch (item) {
                    case "item-1": // 생채소
                        N_bean.setCalories(bean.getDrag5_item_1() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(1.5 * multiplier); // 탄수화물
                        N_bean.setProtein(0.4 * multiplier);  // 단백질
                        N_bean.setFat(0.7 * multiplier);  // 지방
                        N_bean.setCholesterol(0 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(0.6 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.005 * multiplier); // 나트륨
                        N_bean.setSugar(1 * multiplier);  // 설탕당
                        break;
                    case "item-2": // 채소반찬
                        N_bean.setCalories(bean.getDrag5_item_2() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(3.7 * multiplier); // 탄수화물
                        N_bean.setProtein(1.4 * multiplier);  // 단백질
                        N_bean.setFat(0.7 * multiplier);  // 지방
                        N_bean.setCholesterol(0 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(1.2 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.25 * multiplier); // 나트륨
                        N_bean.setSugar(1.2 * multiplier);  // 설탕당
                        break;
                    case "item-3": // 김치절임
                        N_bean.setCalories(bean.getDrag5_item_3() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(4 * multiplier); // 탄수화물
                        N_bean.setProtein(1.6 * multiplier);  // 단백질
                        N_bean.setFat(0.2 * multiplier);  // 지방
                        N_bean.setCholesterol(0 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(1.2 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.6 * multiplier); // 나트륨
                        N_bean.setSugar(1.4 * multiplier);  // 설탕당
                        break;
                    default:
                        break;
                }
                break;
            case "drag6Items":
                switch (item) {
                    case "item-1": // 육류
                        N_bean.setCalories(bean.getDrag6_item_1() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(1.1 * multiplier); // 탄수화물
                        N_bean.setProtein(34.4 * multiplier);  // 단백질
                        N_bean.setFat(56.4 * multiplier);  // 지방
                        N_bean.setCholesterol(0.1 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(0 * multiplier);  // 식이섬유
                        N_bean.setSodium(2 * multiplier); // 나트륨
                        N_bean.setSugar(0 * multiplier);  // 설탕당
                        break;
                    case "item-2": // 해산물류
                        N_bean.setCalories(bean.getDrag6_item_2() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(0.7 * multiplier); // 탄수화물
                        N_bean.setProtein(35.1 * multiplier);  // 단백질
                        N_bean.setFat(25.1 * multiplier);  // 지방
                        N_bean.setCholesterol(0.12 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(1.2 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.8 * multiplier); // 나트륨
                        N_bean.setSugar(0.2 * multiplier);  // 설탕당
                        break;
                    case "item-3": // 계란류
                        N_bean.setCalories(bean.getDrag6_item_3() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(0.4 * multiplier); // 탄수화물
                        N_bean.setProtein(6.2 * multiplier);  // 단백질
                        N_bean.setFat(6.7 * multiplier);  // 지방
                        N_bean.setCholesterol(0.2 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(0 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.2 * multiplier); // 나트륨
                        N_bean.setSugar(0.4 * multiplier);  // 설탕당
                        break;
                    default:
                        break;
                }
                break;
            case "drag7Items":
                switch (item) {
                    case "item-1": // 가당음료
                        N_bean.setCalories(bean.getDrag7_item_1() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(23 * multiplier); // 탄수화물
                        N_bean.setProtein(0 * multiplier);  // 단백질
                        N_bean.setFat(0 * multiplier);  // 지방
                        N_bean.setCholesterol(0 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(0 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.01 * multiplier); // 나트륨
                        N_bean.setSugar(22 * multiplier);  // 설탕당
                        break;
                    case "item-2": // 과자
                        N_bean.setCalories(bean.getDrag7_item_2() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(39 * multiplier); // 탄수화물
                        N_bean.setProtein(3.5 * multiplier);  // 단백질
                        N_bean.setFat(24 * multiplier);  // 지방
                        N_bean.setCholesterol(7.8 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(0 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.3 * multiplier); // 나트륨
                        N_bean.setSugar(3.5 * multiplier);  // 설탕당
                        break;
                    case "item-3": // 사탕
                        N_bean.setCalories(bean.getDrag7_item_3() * multiplier); // 칼로리
                        N_bean.setCarbohydrate(11 * multiplier); // 탄수화물
                        N_bean.setProtein(0 * multiplier);  // 단백질
                        N_bean.setFat(0 * multiplier);  // 지방
                        N_bean.setCholesterol(0 * multiplier);  // 콜레스테롤
                        N_bean.setDietaryFiber(0 * multiplier);  // 식이섬유
                        N_bean.setSodium(0.02 * multiplier); // 나트륨
                        N_bean.setSugar(9 * multiplier);  // 설탕당
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return N_bean;
    }

    // 필요 칼로리 계산기
    public double requiredCalorieCalculator(Nutri bean) {
        double cal = 0;
        double exRate = 0; // PA(신체활동계수): 1.0(비활동적=1), 1.11(저활동적=2), 1.25(활동적=3), 1.48(매우 활동적=4)

        int age = Integer.parseInt(bean.getNutriAge());
        int weight = Integer.parseInt(bean.getNutriWeight());
        int height = Integer.parseInt(bean.getNutriHeight());
        int exerciseRate = Integer.parseInt(bean.getExerciseRate());

        if (bean.getNutriGenderState().equals("1")) {
            switch (exerciseRate) {
                case 1:
                    exRate = 1.0;
                    break;
                case 2:
                    exRate = 1.11;
                    break;
                case 3:
                    exRate = 1.25;
                    break;
                case 4:
                    exRate = 1.48;
                    break;
            }
            cal = 662 - 9.53 * age + exRate * (15.91 * weight + 539.6 * height * 0.01);
        } else if (bean.getNutriGenderState().equals("2")) {
            switch (exerciseRate) {
                case 1:
                    exRate = 1.0;
                    break;
                case 2:
                    exRate = 1.11;
                    break;
                case 3:
                    exRate = 1.25;
                    break;
                case 4:
                    exRate = 1.48;
                    break;
            }
            cal = 354 - 6.91 * age + exRate * (9.36 * weight + 726 * height * 0.01);
        }

        return cal;
    }

}
