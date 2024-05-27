package com.final_project.controller;

import com.final_project.Service.DiaryService;
import com.final_project.entity.Diary;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/diary")
public class DiaryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DiaryService ds;

    // 일반 리스트 조회
    @GetMapping(value = "/diaryList/{memno}")
    public ResponseEntity<?> SelectAll(@PathVariable String memno) {

        try {

            List<Diary> diaryList = ds.SelectAll(memno);
            List<Diary> diaryDate = ds.SelectDate(memno);


            System.out.println(diaryList);
            System.out.println(diaryDate);

            Map<String, Object> response = new HashMap<>();

            response.put("diaryList", diaryList);
            response.put("diaryDate", diaryDate);

            return ResponseEntity.ok(response); // Map을 JSON 형식으로 반환

        } catch (Exception e) {
            logger.error("게시판 목록을 가져오는 데 실패했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/diaryselect")
    public ResponseEntity<?> selectDiary(@RequestBody Diary diary) {
        try {
            List<Diary> diarySelect = ds.Select(diary);
            return ResponseEntity.ok(diarySelect); // 데이터를 JSON 형식으로 반환
        } catch (Exception e) {
            logger.error("Error during board insertion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing your request");
        }
    }

    // 일기 등록
    @PostMapping("/diaryInsert")
    public ResponseEntity<?> createBoard(@RequestBody Diary diary) {
        try {
            // 비즈니스 로직 실행
            int cnt = ds.Insert(diary);
            if (cnt == 1) {
                return ResponseEntity.ok("Insert successful");
            } else {
                throw new Exception("Insert failed");
            }
        } catch (Exception e) {
            logger.error("Error during board insertion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing your request");
        }
    }

    // 일기 삭제
    @DeleteMapping(value = "/diarydelete/{diaryno}")
    public ResponseEntity<?> deleteDiary(@PathVariable("diaryno") int diaryno){
        System.out.println("Deleting diary entry with ID: " + diaryno);
        try {
            int cnt = ds.Delete(diaryno);
            if(cnt == 1){
                return ResponseEntity.ok("Delete successful");
            } else {
                throw new Exception("Delete failed");
            }
        } catch (Exception e) {
            logger.error("게시물 삭제 중 오류가 발생했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing your request");
        }
    }

    // 일기 수정
    @PostMapping(value = "/diaryUpdate")
    public ResponseEntity<?> updateDiary(@RequestBody Diary diary){
        try {
            int cnt = ds.Update(diary);
            if(cnt == 1){
                return ResponseEntity.ok("Update successful");
            } else {
                throw new Exception("Update failed");
            }
        } catch (Exception e) {
            logger.error("일기 수정 중 오류가 발생했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing your request");
        }
    }
}
