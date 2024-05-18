package com.final_project.mapper;

import com.final_project.entity.Board;
import com.final_project.entity.Cart;
import com.final_project.entity.Products;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CartMapperInterface {
    @Insert("INSERT INTO tcrt (mbrno, crtcd, crtqt) VALUES (#{cart.mbrno}, #{cart.crtcd}, #{cart.crtqt})")
    int Insert(@Param("cart") Cart cart);

    @Insert("DELETE FROM tcrt WHERE mbrno = #{cart.mbrno} AND crtcd = #{cart.crtcd}")
    int Delete(@Param("cart") Cart cart);
}