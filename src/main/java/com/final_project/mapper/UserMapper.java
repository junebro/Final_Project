package com.final_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("SELECT mempw FROM tmem WHERE memno = #{memno}")
    String getPasswordById(@Param("memno") String memno);

    @Update("UPDATE tmem SET mempw = #{mempw} WHERE memno = #{memno}")
    void updatePassword(@Param("memno") String memno, @Param("mempw") String mempw);
}
