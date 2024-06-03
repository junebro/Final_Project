package com.final_project.Service;

import com.final_project.dto.NutrientDTO;
import com.final_project.dto.NutritionDTO;
import com.final_project.mapper.NutritionInterfaceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NutritionService {

    private final  NutritionInterfaceMapper nm;

    public List<NutritionDTO> SelectAll(String userNo) {
        return nm.SelectAll(userNo);
    }

    public NutrientDTO nutrientSelect(String nNo) {
        return nm.nutrientSelect(nNo);
    }
}
