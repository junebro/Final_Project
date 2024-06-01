package com.final_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
    @Select("SELECT memberNick FROM tmem WHERE memno = #{memno}")
    String getMemberNickByMemNo(String memno);
}