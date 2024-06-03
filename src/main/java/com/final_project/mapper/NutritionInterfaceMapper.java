package com.final_project.mapper;

import com.final_project.dto.NutrientDTO;
import com.final_project.dto.NutritionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NutritionInterfaceMapper {

    @Select("SELECT \n" +
            " ROW_NUMBER() OVER (ORDER BY n_no) AS seq, \n" +
            " n_no, creation_date, memno\n" +
            " FROM nutrient \n" +
            " WHERE memno =#{userNo} \n" +
            " ORDER BY n_no DESC;")
    List<NutritionDTO> SelectAll(String userNo);

    @Select("SELECT * FROM nutrient WHERE n_no = #{nNo};")
    NutrientDTO nutrientSelect(String nNo);
}
