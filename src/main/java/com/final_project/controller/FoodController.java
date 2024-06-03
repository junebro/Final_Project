package com.final_project.controller;

import com.final_project.Service.CartService;
import com.final_project.Service.FoodService;
import com.final_project.dto.CartDTO;
import com.final_project.dto.FoodDTO;
import com.final_project.dto.NutrientDTO;
import com.final_project.dto.ProductsDTO;
import com.final_project.entity.Nutrient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API 컨트롤러로 정의
@RequestMapping("/diet") // 이 컨트롤러의 기본 URL 경로 설정
public class FoodController {
    @Autowired // FoodService 인스턴스 자동 주입
    private FoodService foodService;
    @Autowired
    private CartService cartService;

    @GetMapping("/daily/{userNo}") // HTTP GET 요청을 /daily 경로로 매핑
    public List<List<FoodDTO>> getDailyMealPlan(@PathVariable String userNo) {

        List<List<FoodDTO>> food = foodService.generateDailyMealPlan(); // 식사 계획 생성 및 반환

        return food;
    }

    @GetMapping("/dailyUser/{userNo}")
    public ResponseEntity<?> nutrientMemSelect(@PathVariable String userNo) {
        NutrientDTO  Nutrient = foodService.memSelect(userNo);

        if (Nutrient == null) {
            // 클라이언트가 처리할 수 있는 JSON 형태의 에러 메시지를 반환
            return ResponseEntity.ok("{\"message\": \"null\"}");
        }

        return ResponseEntity.ok(Nutrient);  // 데이터가 있는 경우 정상적으로 DTO 반환
    }

    @PostMapping("/foodcartinsert")
    public String addCartItems(@RequestBody List<CartDTO> cartItems) {
        cartService.insertCartItems(cartItems);
        return "Cart items successfully inserted";
    }
}
