package com.final_project.mapper;

import com.final_project.dto.OrderDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapperInterface {
    @Insert("INSERT INTO TORD (ordno, memno, ordpr, orddt, ordst) " +
            "VALUES (#{ordno}, #{memno}, #{ordpr}, #{orddt}, #{ordst})")
    void save(OrderDTO order);

    @Select("INSERT INTO order_sequence () VALUES (); SELECT LAST_INSERT_ID()")
    Long getNextOrderSequence();
}
