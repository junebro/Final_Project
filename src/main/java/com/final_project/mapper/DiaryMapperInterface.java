package com.final_project.mapper;

import com.final_project.entity.Board;
import com.final_project.entity.Cart;
import com.final_project.entity.Diary;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DiaryMapperInterface {

    @Select(value = "SELECT \n" +
                                " A.*, B.mood, b.moodimg \n" +
                                " FROM b_diary a \n" +
                                " LEFT OUTER JOIN b_sticker b ON a.moodcode = b.moodcode\n" +
                                " WHERE a.memno = #{memno};")
    List<Diary> SelectAll(@Param("memno") String memno);


    @Select(value = "SELECT \n" +
            " A.memno, A.diarydate, B.moodimg\n" +
            " FROM b_diary A\n" +
            " LEFT OUTER JOIN b_sticker B ON A.moodcode = B.moodcode\n" +
            " WHERE A.memno =#{memno};")
    List<Diary> SelectDate(@Param("memno") String memno);

    @Select(value = "SELECT \n" +
                " A.*, B.mood, b.moodimg \n" +
                " FROM b_diary a \n" +
                " LEFT OUTER JOIN b_sticker b ON a.moodcode = b.moodcode" +
                " WHERE a.memno = #{diary.memno} \n" +
                " AND diarydate = #{diary.diarydate}")
    List<Diary> Select(@Param("diary") Diary diary);

    @Insert("INSERT INTO B_DIARY  (diaryno,  memno, diarydetail, diarydate, moodcode) \n" +
                    "VALUES(#{diary.diaryno}, #{diary.memno}, #{diary.diarydetail}, #{diary.diarydate} ,  #{diary.moodcode})")
    int Insert(@Param("diary") final Diary diary);
}
