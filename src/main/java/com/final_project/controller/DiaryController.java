package com.final_project.controller;

import com.final_project.Service.DiaryService;
import com.final_project.entity.Board;
import com.final_project.entity.Cart;
import com.final_project.entity.Diary;
import com.final_project.entity.Products;
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

        System.out.println("11111111111111111111");
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

    // 다이어리 등록
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
}
