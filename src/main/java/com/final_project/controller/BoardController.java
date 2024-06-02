package com.final_project.controller;

import com.final_project.Service.BoardService;
import com.final_project.Service.FileService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class BoardController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BoardService bs;
    private final FileService fileService;

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
            // 페이징 정보 포함하여 반환
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

    // 게시물 생성
    @PostMapping("/boardInsert")
    public ResponseEntity<?> createBoard(@RequestPart("board" ) Board board,
                                         @RequestPart(value = "file1", required = false ) MultipartFile file1,
                                         @RequestPart(value ="file2", required = false) MultipartFile file2,
                                         @RequestPart(value="file3", required = false) MultipartFile file3) {
        try {
            // 파일 배열 생성
            MultipartFile[] files = {file1, file2, file3};

            // 파일 배열에서 빈 파일을 제거하는 로직 추가
            List<MultipartFile> validFiles = Arrays.stream(files)
                    .filter(file -> file != null && !file.isEmpty())
                    .collect(Collectors.toList());


            // 비즈니스 로직 실행
            int cnt = bs.Insert(board, validFiles.toArray(new MultipartFile[0]));
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

    // 게시물 상세 보기
    @GetMapping(value = "/boardDetail/{bono}")
    public ResponseEntity<Board> SelectOne(@PathVariable("bono") Integer bono){
        try {
            Board board = bs.SelectOne(bono);
            if (board != null) {
                bs.updateViewCount(bono);  // 조회수 증가
            }
            return ResponseEntity.ok(board);
        } catch (Exception e) {
            logger.error("게시물 조회에 실패했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 수정 페이지 요청 (게시물 조회)
    @GetMapping(value = "/boardUpdate/{bono}")
    public ResponseEntity<Board> doGetUpdate(@PathVariable("bono") Integer bono){
        try {
            Board board = bs.SelectOne(bono);
            logger.info("Fetched board for update: {}", board);
            return ResponseEntity.ok(board);
        } catch (Exception e) {
            logger.error("게시물 조회에 실패했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 게시물 수정
    @PostMapping(value = "/boardUpdate")
    public ResponseEntity<?> doPostUpdate(@RequestPart("board") Board board,
                                          @RequestPart(value = "file1", required = false) MultipartFile file1,
                                          @RequestPart(value="file2", required = false) MultipartFile file2,
                                          @RequestPart(value="file3", required = false) MultipartFile file3) {
        try {
            // 파일 배열 생성
            MultipartFile[] files = {file1, file2, file3};

            // 파일 배열에서 빈 파일을 제거하는 로직 추가
            List<MultipartFile> validFiles = Arrays.stream(files)
                    .filter(file -> file != null && !file.isEmpty())
                    .collect(Collectors.toList());

            // 파일이 있을 경우 처리
            if (!validFiles.isEmpty()) {
                if (validFiles.size() > 0) {
                    String imageName = fileService.uploadFile(validFiles.get(0));
                    board.setBoimage01(imageName);
                    board.setThumb_boimage01(fileService.uploadThumbnailFile(imageName));
                }
                if (validFiles.size() > 1) {
                    String imageName = fileService.uploadFile(validFiles.get(1));
                    board.setBoimage02(imageName);
                    board.setThumb_boimage02(fileService.uploadThumbnailFile(imageName));
                }
                if (validFiles.size() > 2) {
                    String imageName = fileService.uploadFile(validFiles.get(2));
                    board.setBoimage03(imageName);
                    board.setThumb_boimage03(fileService.uploadThumbnailFile(imageName));
                }
            }

            int cnt = bs.Update(board, validFiles.toArray(new MultipartFile[0]));
            if(cnt == 1) {
                return ResponseEntity.ok("Update successful");
            } else {
                throw new Exception("Update failed");
            }
        } catch (Exception e) {
            logger.error("Error updating board", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing your request");
        }
    }

    // 게시물 삭제
    @DeleteMapping(value = "/boardDelete/{bono}")
    public ResponseEntity<?> Delete(@PathVariable("bono") Integer bono){
        try {
            int cnt = bs.Delete(bono);
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

    // 좋아요 업데이트
    @PostMapping("/likes/{bono}")
    public ResponseEntity<?> updateLikes(@PathVariable("bono") Integer bono, @RequestBody Map<String, Object> payload) {
        try {
            boolean increase = Boolean.parseBoolean(payload.get("liked").toString());
            int updateResult = bs.updateLikeCount(bono, increase);
            if (updateResult == 1) {
                return ResponseEntity.ok("Like updated successfully");
            } else {
                throw new Exception("Failed to update likes");
            }
        } catch (Exception e) {
            logger.error("Error updating likes for bono: {}", bono, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing your request");
        }
    }
}

