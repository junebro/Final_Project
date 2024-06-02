package com.final_project.mapper;

import com.final_project.dto.NutrientDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
    @Select("SELECT memberNick FROM tmem WHERE memno = #{memno}")
    String getMemberNickByMemNo(String memno);

    @Insert("INSERT INTO Nutrient (calories, carbohydrate, protein, fat, Sodium, sugar, cholesterol, DietaryFiber, requiredCalories, requiredProtein, minProtein, requiredDietaryFiber, carbohydrateRate, proteinRate, fatRate, memno) " +
            "VALUES (#{calories}, #{carbohydrate}, #{protein}, #{fat}, #{Sodium}, #{sugar}, #{cholesterol}, #{DietaryFiber}, #{requiredCalories}, #{requiredProtein}, #{minProtein}, #{requiredDietaryFiber}, #{carbohydrateRate}, #{proteinRate}, #{fatRate}, #{memno})")
    void insertNutientInfo(NutrientDTO dto);

}