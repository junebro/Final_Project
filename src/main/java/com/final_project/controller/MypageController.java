package com.final_project.controller;

import com.final_project.Service.DiaryService;
import com.final_project.Service.MypageService;
import com.final_project.dto.MemberDTO;
import com.final_project.dto.MypageDTO;
import com.final_project.entity.Diary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/mypage")
public class MypageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MypageService mypagemi;

    @GetMapping(value = "/writeList/{userNo}")
    public ResponseEntity<?> SelectAll(@PathVariable String userNo) {
        System.out.println("작성글 조회 : " + userNo);
        try {

            List<MypageDTO> writeList = mypagemi.SelectAll(userNo);
            return ResponseEntity.ok(writeList); // Map을 JSON 형식으로 반환

        } catch (Exception e) {
            logger.error("작성글 목록을 가져오는 데 실패했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "likeList/{userNo}")
    public ResponseEntity<?> SelectLike(@PathVariable String userNo) {
        System.out.println("좋아요 조회 : " + userNo);
        try {

            List<MypageDTO> likeList = mypagemi.SelectLike(userNo);
            System.out.println(likeList);

            Map<String, Object> response = new HashMap<>();

            response.put("likeList", likeList);


            return ResponseEntity.ok(response); // Map을 JSON 형식으로 반환

        } catch (Exception e) {
            logger.error("작성글 목록을 가져오는 데 실패했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // 주문내역 조회
    @GetMapping(value = "/myOrderList/{userNo}")
    public ResponseEntity<?> SelectOrderAll(@PathVariable String userNo) {
        System.out.println("작성글 조회 : " + userNo);
        try {

            List<MypageDTO> myOrderList = mypagemi.SelectOrderAll(userNo);
            return ResponseEntity.ok(myOrderList);

        } catch (Exception e) {
            logger.error("작성글 목록을 가져오는 데 실패했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
