package com.final_project.mapper;

import com.final_project.entity.Products;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductsMapperInterface {
    @Select("SELECT * FROM products")
    List<Products> SelectAll();
}
