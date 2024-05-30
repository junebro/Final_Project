package com.final_project.mapper;


import com.final_project.entity.Diary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DiaryMapperInterface {

    @Select(value = "SELECT \n" +
            " A.*, B.mood, b.moodimg \n" +
            " FROM tdiary a \n" +
            " LEFT OUTER JOIN sticker b ON a.moodcode = b.moodcode\n" +
            " WHERE a.memno = #{userNo};")
    List<Diary> SelectAll(@Param("userNo") String userNo);


    @Select(value = "SELECT \n" +
            " A.memno, A.diarydate, B.moodimg\n" +
            " FROM tdiary A\n" +
            " LEFT OUTER JOIN sticker B ON A.moodcode = B.moodcode\n" +
            " WHERE A.memno =#{userNo};")
    List<Diary> SelectDate(@Param("userNo") String userNo);

    @Select(value = "SELECT \n" +
                " A.*, B.mood, b.moodimg \n" +
                " FROM tdiary a \n" +
                " LEFT OUTER JOIN sticker b ON a.moodcode = b.moodcode" +
                " WHERE a.memno = #{diary.memno} \n" +
                " AND diarydate = #{diary.diarydate}")
    List<Diary> Select(@Param("diary") Diary diary);

    @Insert("INSERT INTO tdiary  (diaryno,  memno, diarydetail, diarydate, moodcode) \n" +
                    "VALUES(#{diary.diaryno}, #{diary.memno}, #{diary.diarydetail}, #{diary.diarydate} ,  #{diary.moodcode})")
    int Insert(@Param("diary") Diary diary);

    // 일기 삭제
    @Delete("DELETE FROM tdiary WHERE diaryno = #{diaryno}")
    int Delete(@Param("diaryno") Integer diaryno);

    // 일기 수정
    @Update("UPDATE tdiary SET diarydetail = #{diary.diarydetail}, moodcode = #{diary.moodcode} where diaryno = #{diary.diaryno};")
    int Update(@Param("diary") Diary diary);
}
