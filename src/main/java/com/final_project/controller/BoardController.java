package com.final_project.controller;

import com.final_project.Service.BoardService;
import com.final_project.Service.DiaryService;
import com.final_project.entity.Board;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class BoardController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BoardService bs;

    // 페이징 처리된 리스트 조회
    @GetMapping(value = "/boardList/paged")
    public ResponseEntity<?> SelectAllPaged(Pageable pageable){
        try {
            // 페이징 처리된 결과 조회
            Page<Board> boardPage = bs.SelectAll(pageable);
            logger.info("Page content: {}", boardPage.getContent());
            Map<String, Object> response = new HashMap<>();
            response.put("posts", boardPage.getContent());
            response.put("totalCount", boardPage.getTotalElements());
            // 페이징 정보 포함하여 반환dd
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("페이징 처리된 게시판 목록을 가져오는 데 실패했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터를 가져오는 데 실패했습니다.");
        }
    }

    // 일반 리스트 조회
    @GetMapping(value = "/boardList")
    public ResponseEntity<List<Board>> SelectAll() {
        try {
            List<Board> boardList = bs.SelectAll();
            return ResponseEntity.ok(boardList); // 데이터를 JSON 형식으로 반환
        } catch (Exception e) {
            logger.error("게시판 목록을 가져오는 데 실패했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/boardInsert")
    public ResponseEntity<?> createBoard(@RequestBody Board board) {
        try {
            // 비즈니스 로직 실행
            int cnt = bs.Insert(board);
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

    @GetMapping(value = "/boardInsert")
    public String doGetInsert(Model model){
        model.addAttribute("board", new Board());
        return "/board/boardInsert" ;
    }
    @GetMapping(value = "/boardDetail/{bono}")
    public String SelectOne(@PathVariable("bono") Integer bono, Model model){
        Board board = bs.SelectOne(bono);
        model.addAttribute("board", board);
        return "/board/boardDetail" ;
    }

    // 수정하기
    @GetMapping(value = "/boardUpdate/{bono}")
    public String doGetUpdate(@PathVariable("bono") Integer bono, Model model){
        Board board = bs.SelectOne(bono);
        model.addAttribute("board", board);
        return "/board/boardUpdate" ;
    }

    // 폼 양식을 수정하고 [수정] 버튼을 클릭하였습니다.
    @PostMapping(value = "/boardUpdate")
    public String doPostUpdate(Board board){
        // @Valid 를 사용하여 유효성 검사도 하면 좋습니다.
        System.out.println("board : " + board);
        int cnt = -999 ;
        cnt = bs.Update(board);

        if(cnt == 1){
            return "redirect:/board/boardList" ;
        }else{
            return "/board/boardUpdate" ;
        }
    }

    @GetMapping(value = "/boardDelete/{bono}") // 삭제하기
    public String Delete(@PathVariable("bono") Integer bono){
        int cnt = -999;
        cnt = bs.Delete(bono);
        return "redirect:/board/boardList" ;
    }
}


