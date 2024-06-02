package com.final_project.controller;

import com.final_project.Service.NutriService;
import com.final_project.dto.NutrientDTO;
import com.final_project.entity.Nutri;
import com.final_project.entity.Nutrient;
import com.final_project.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/nutri")
public class NutriDataController {
    @Autowired
    private MemberMapper nutriService;
    @PostMapping("/sendData02")
    @ResponseBody
    public ResponseEntity<NutrientDTO> updateDb(@RequestBody NutrientDTO jsonData) {
        try {
            System.out.println(jsonData);
            nutriService.insertNutientInfo(jsonData);
            return ResponseEntity.ok(jsonData);
        } catch (Exception e) {
            return null;
        }
    }
}
