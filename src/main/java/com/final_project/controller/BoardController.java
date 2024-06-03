package com.final_project.controller;

import com.final_project.Service.BoardService;
import com.final_project.Service.FileService;
import com.final_project.Service.MemberService;
import com.final_project.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    private final MemberService memberService;

    // 페이징 처리된 리스트 조회
    @GetMapping(value = "/boardList/paged")
    public ResponseEntity<?> SelectAllPaged(Pageable pageable, @RequestParam(required = false) String orderBy){
        Page<BoardDTO> boardPage = bs.SelectAll(pageable, orderBy);
        Map<String, Object> response = new HashMap<>();
        response.put("posts", boardPage.getContent());
        response.put("totalCount", boardPage.getTotalElements());
        return ResponseEntity.ok(response);
    }

    // 일반 리스트 조회
    @GetMapping(value = "/boardList")
    public ResponseEntity<List<BoardDTO>> SelectAll(@RequestParam(required = false, defaultValue = "recent") String orderBy) {
        try {
            List<BoardDTO> boardList = bs.SelectAll(orderBy);
            return ResponseEntity.ok(boardList); // 데이터를 JSON 형식으로 반환
        } catch (Exception e) {
            logger.error("게시판 목록을 가져오는 데 실패했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 게시물 검색
    @GetMapping("/search")
    public ResponseEntity<?> searchBoards(@RequestParam("keyword") String keyword) {
        try {
            List<BoardDTO> searchResults = bs.searchByTitleOrContent(keyword);
            if (searchResults.isEmpty()) {
                logger.info("No boards found for keyword: {}", keyword);
                return ResponseEntity.ok("No results found");
            } else {
                logger.info("Found {} boards for keyword: {}", searchResults.size(), keyword);
                return ResponseEntity.ok(searchResults);
            }
        } catch (Exception e) {
            logger.error("Error searching boards with keyword: {}", keyword, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing your search request");
        }
    }

    // 게시물 생성
    @PostMapping("/boardInsert")
    public ResponseEntity<?> createBoard(@RequestPart("board" ) BoardDTO boardDto,
                                         @RequestPart(value = "file1", required = false ) MultipartFile file1,
                                         @RequestPart(value ="file2", required = false) MultipartFile file2,
                                         @RequestPart(value="file3", required = false) MultipartFile file3) {
        try {
            // 현재 인증된 사용자 정보 가져오기
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userId = userDetails.getUsername();  // 사용자 ID가 반환됨

            // 사용자 ID를 이용하여 memNo 조회
            Integer memNo = memberService.findMemNoByUserId(userId);
            if (memNo == null) {
                logger.error("No member found with userId: {}", userId);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Member not found");
            }

            // BoardDTO 설정
            boardDto.setMemNo(memNo.toString());
            logger.info("Setting memNo for the board: {}", memNo);


            // 파일 배열 생성
            MultipartFile[] files = {file1, file2, file3};

            // 파일 배열에서 빈 파일을 제거하는 로직 추가
            List<MultipartFile> validFiles = Arrays.stream(files)
                    .filter(file -> file != null && !file.isEmpty())
                    .collect(Collectors.toList());

            // 비즈니스 로직 실행
            int cnt = bs.Insert(boardDto, validFiles.toArray(new MultipartFile[0]));
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

    // Request Header에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @GetMapping(value = "/boardInsert")
    public String doGetInsert(Model model){
        model.addAttribute("board", new BoardDTO());
        return "/board/boardInsert" ;
    }

    // 게시물 상세 보기
    @GetMapping(value = "/boardDetail/{bono}")
    public ResponseEntity<BoardDTO> SelectOne(@PathVariable("bono") Integer bono){
        try {
            BoardDTO board = bs.SelectOne(bono);
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
    public ResponseEntity<BoardDTO> doGetUpdate(@PathVariable("bono") Integer bono){
        try {
            BoardDTO board = bs.SelectOne(bono);
            logger.info("Fetched board for update: {}", board);
            return ResponseEntity.ok(board);
        } catch (Exception e) {
            logger.error("게시물 조회에 실패했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 게시물 수정
    @PostMapping(value = "/boardUpdate")
    public ResponseEntity<?> doPostUpdate(@RequestPart("board") BoardDTO boardDto,
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
                    boardDto.setBoimage01(imageName);
                    boardDto.setThumb_boimage01(fileService.uploadThumbnailFile(imageName));
                }
                if (validFiles.size() > 1) {
                    String imageName = fileService.uploadFile(validFiles.get(1));
                    boardDto.setBoimage02(imageName);
                    boardDto.setThumb_boimage02(fileService.uploadThumbnailFile(imageName));
                }
                if (validFiles.size() > 2) {
                    String imageName = fileService.uploadFile(validFiles.get(2));
                    boardDto.setBoimage03(imageName);
                    boardDto.setThumb_boimage03(fileService.uploadThumbnailFile(imageName));
                }
            }

            int cnt = bs.Update(boardDto, validFiles.toArray(new MultipartFile[0]));
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

    // 좋아요
    @PostMapping("/likes/{bono}")
    public ResponseEntity<?> toggleLike(@PathVariable("bono") Integer bono) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer memNo = memberService.findMemNoByUserId(userDetails.getUsername());
        if (memNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Member not found");
        }

        boolean alreadyLiked = bs.checkLike(bono, memNo);
        try {
            if(!alreadyLiked) {
                bs.addLike(bono, memNo);
                return ResponseEntity.ok("Like added successfully");
            } else {
                bs.removeLike(bono, memNo);
                return ResponseEntity.ok("Like removed successfully");
            }
        } catch (Exception e) {
            logger.error("Error updating likes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing your request");
        }
    }


}
