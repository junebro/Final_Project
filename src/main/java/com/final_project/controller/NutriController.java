package com.final_project.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.final_project.entity.Nutri;
import com.final_project.entity.Nutrient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/nutri")
public class NutriController {
    // JSON 문자열을 Map으로 변환
    public Map<String, Map<String, List<String>>> DropAdj(Map<String, Map<String, List<String>>> drop) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Map을 JSON 문자열로 변환
        String jsonDrop = objectMapper.writeValueAsString(drop);

        // JSON 문자열을 다시 Map으로 변환
        Map<String, Map<String, List<String>>> resultMap = objectMapper.readValue(jsonDrop, new TypeReference<>() {});

        // 결과를 저장할 새로운 Map
        Map<String, Map<String, List<String>>> transformedMap = new HashMap<>();

        for (Map.Entry<String, Map<String, List<String>>> entry : resultMap.entrySet()) {
            String dragKey = entry.getKey();
            Map<String, List<String>> dropMap = entry.getValue();

            // 기존의 Map에 없는 경우 새로운 HashMap을 추가
            transformedMap.putIfAbsent(dragKey, new HashMap<>());

            for (Map.Entry<String, List<String>> dropEntry : dropMap.entrySet()) {
                String dropKey = dropEntry.getKey();
                List<String> values = dropEntry.getValue();

                // 드롭 키에 대한 리스트를 가져오거나 새로 생성
                transformedMap.get(dragKey).putIfAbsent(dropKey, new ArrayList<>());

                // 리스트에 값 추가
                transformedMap.get(dragKey).get(dropKey).addAll(values);
            }
        }
        return transformedMap;
    }

    @PostMapping("/sendData01")
    @ResponseBody
    public ResponseEntity<Nutrient> receiveData(@RequestBody Nutri jsonData) {
        NutriCalcController nc = new NutriCalcController();
        NutriRequieredController nrc = new NutriRequieredController();
        // 평균 영양소 값을 갖는 새로운 Nutrient 객체 생성
        Nutrient averageNutrients = new Nutrient();
        try {
            System.out.println("Received Data: " + jsonData);
            System.out.println("drop data : " + jsonData.getDrop());
            Map<String, Map<String, List<String>>> sortedData = DropAdj(jsonData.getDrop());
            System.out.println("맵으로 정리한 드래그 아이템 : " + sortedData);
            Nutrient calcResult = nc.CalorieCalculator(sortedData);
            // 섭취한 칼로리의 7일 평균량
            double cal = (calcResult.getCalories())/7;
            System.out.println("섭취 칼로리 : " + cal);
            double reqCal = nc.requiredCalorieCalculator(jsonData);
            System.out.println("필요 칼로리 : " + reqCal );
            // 섭취 영양소 배열
            double carbohydrate=(calcResult.getCarbohydrate())/7; // 탄수화물
            double protein=(calcResult.getProtein())/7; // 단백질
            double fat=(calcResult.getFat())/7; // 지방
            double Sodium=(calcResult.getSodium())/7; // 나트륨
            double sugar=(calcResult.getSugar())/7; // 설탕당
            double cholesterol=(calcResult.getCholesterol())/7; // 콜레스테롤
            double DietaryFiber=(calcResult.getDietaryFiber())/7; // 식이섬유
            System.out.println("탄수화물 : " + carbohydrate + "\n단백질 : " + protein+ "\n지방 : " +fat + "\n나트륨 : " + Sodium+ "\n설탕당 : " + sugar+ "\n콜레스테롤 : " + cholesterol+ "\n식이섬유 : " + DietaryFiber );
            averageNutrients.setCalories(cal);
            averageNutrients.setCarbohydrate(carbohydrate);
            averageNutrients.setProtein(protein);
            averageNutrients.setFat(fat);
            averageNutrients.setSodium(Sodium);
            averageNutrients.setSugar(sugar);
            averageNutrients.setCholesterol(cholesterol);
            averageNutrients.setDietaryFiber(DietaryFiber);
            averageNutrients.setRequiredCalories(reqCal);


            // 필요 영양소 계산
            Nutrient requiredResult = nrc.requiredNutrient(jsonData , averageNutrients);
            // 단백질 요구량 설정
            averageNutrients.setRequiredProtein(requiredResult.getRequiredProtein());
            averageNutrients.setMinProtein(requiredResult.getMinProtein());
            // 식이섬유 요구량
            averageNutrients.setRequiredDietaryFiber(requiredResult.getRequiredDietaryFiber());
            // 탄단지 비율
            averageNutrients.setCarbohydrateRate(requiredResult.getCarbohydrateRate());
            averageNutrients.setProteinRate(requiredResult.getProteinRate());
            averageNutrients.setFatRate(requiredResult.getFatRate());
            // 확인
            System.out.println("단백질 요구량 : " + requiredResult.getMinProtein() + " ~ " + requiredResult.getRequiredProtein());
            System.out.println("식이섬유 요구량 : " + requiredResult.getRequiredDietaryFiber());
            System.out.println("탄수화물 : " +requiredResult.getCarbohydrateRate()+ "% " + "\n단백질 : " + requiredResult.getProteinRate() + "% " + "\n지방 : " + requiredResult.getFatRate() + "%");

            return new ResponseEntity<>(averageNutrients, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
