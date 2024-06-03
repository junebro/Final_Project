package com.final_project.mapper;

import com.final_project.dto.FoodDTO;
import com.final_project.dto.NutrientDTO;
import com.final_project.dto.ProductsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface FoodMapper {
    // 칼로리 범위에 따라 음식을 무작위로 하나 선택하는 SQL 쿼리
    @Select("SELECT \n" +
            " B.prostp, A.procd, B.pronm, B.propr, B.proimg, \n" +
            " CONVERT(A.pifimg1 USING utf8) AS pifimg1, \n" +
            " CONVERT(A.pifimg2 USING utf8) AS pifimg2, \n" +
            " CONVERT(A.pifimg3 USING utf8) AS pifimg3,\n" +
            "  A.piftt, A.pifcal, A.pifcal, A.piftan, A.pifsu, A.piffat, A.piftrf, A.pifsat, A.pifclt, A.pifprt\n" +
            " FROM TPIF A\n" +
            " LEFT OUTER JOIN TPRO B ON A.procd = B.procd \n" +
            " WHERE A.pifcal BETWEEN #{minCalories} AND #{maxCalories}\n" +
            " ORDER BY RAND() \n" +
            " LIMIT 1;")
    FoodDTO findFoodByCalorieRange(int minCalories, int maxCalories, Set<String> selectedIds);

    @Select("SELECT A.*, B.membernick FROM nutrient A\n" +
            " LEFT OUTER JOIN TMEM B ON A.memno = B.memno\n" +
            " WHERE A.memno = #{userNo}\n" +
            " ORDER BY n_no \n" +
            " DESC LIMIT 1;")
    NutrientDTO memSelect(String userNo);
}
