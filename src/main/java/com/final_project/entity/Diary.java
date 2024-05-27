package com.final_project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class Diary {
    @Id
    private int diaryno; // 일기 번호
    private int memno;
    private String diarydetail; // 일기 내용
    private String diarydate; // 작성일
    private int moodcode; // 상태코드
    private String moodimg; // 스티커
    private String mood; // 상태 설명
}
