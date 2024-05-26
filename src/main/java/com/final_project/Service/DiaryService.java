package com.final_project.Service;

import com.final_project.mapper.DiaryMapperInterface;
import com.final_project.entity.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryMapperInterface dmi;

    public List<Diary> SelectAll(String memno) {
        return dmi.SelectAll(memno);
    }

    public List<Diary> Select(Diary diary) {
        return dmi.Select(diary);
    }

    public List<Diary> SelectDate(String memno) {
        return dmi.SelectDate(memno);
    }

    public int Insert(Diary diary) {
        return dmi.Insert(diary);
    }

    public int Delete(int diaryno) {return dmi.Delete(diaryno); }

    public int Update(Diary diary) {return dmi.Update(diary); }
}
