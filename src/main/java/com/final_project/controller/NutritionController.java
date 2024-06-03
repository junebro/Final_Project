package com.final_project.controller;

import com.final_project.Service.NutritionService;
import com.final_project.dto.NutrientDTO;
import com.final_project.dto.NutritionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/nutrition")
public class NutritionController {

    private final NutritionService ns;

    @GetMapping(value = "/nutritionselect/{userNo}")
    public ResponseEntity<List<NutritionDTO>> selectAll(@PathVariable String userNo) {
        System.out.println("11111111111111111");
        List<NutritionDTO>nutritionList  = ns.SelectAll(userNo);
        return ResponseEntity.ok(nutritionList);
    }

    @GetMapping(value = "/nutrientselect/{nNo}")
    public ResponseEntity<NutrientDTO> nutrientSelect(@PathVariable String nNo) {
        System.out.println("22222222222222222");
        System.out.println(nNo);
        NutrientDTO nutritionList  = ns.nutrientSelect(nNo);
        System.out.println(nutritionList);
        return ResponseEntity.ok(nutritionList);
    }
}
