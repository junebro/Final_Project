package com.final_project.Service;

import com.final_project.entity.Board;
import com.final_project.mapper.BoardMapperInterface;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapperInterface bmi;
    private final FileService fileService;
    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    // 페이징 처리된 리스트 조회
    public Page<Board> SelectAll(Pageable pageable){
        logger.info("Starting pagination with page number: {} and page size: {}", pageable.getPageNumber(), pageable.getPageSize());

        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<Board> boards = bmi.SelectAll();// 실제 데이터 조회
        long total = countBoards(); /// 전체 게시물 수 조회

        logger.info("Fetched {} boards with total of {} entries", boards.size(), total);
        return new PageImpl<>(boards, pageable, total);
    }

    // 전체 게시물 수 조회
    private long countBoards() {
        long count = bmi.count();
        logger.info("Total number of boards: {}", count);
        return bmi.count(); // 전체 게시물 수를 반환하는 MyBatis 메소드
    }

    public List<Board> SelectAll(){
        List<Board> boards = bmi.SelectAll();
        logger.info("Fetched all boards, count: {}", boards.size());
        return boards;
    }

    // 게시물 등록
    public int Insert(Board board, MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String imageName = saveFile(file);
                if (board.getBoimage01() == null) {
                    board.setBoimage01(imageName);
                    board.setThumb_boimage01(fileService.uploadThumbnailFile(imageName));
                } else if (board.getBoimage02() == null) {
                    board.setBoimage02(imageName);
                    board.setThumb_boimage02(fileService.uploadThumbnailFile(imageName));
                } else if (board.getBoimage03() == null) {
                    board.setBoimage03(imageName);
                    board.setThumb_boimage03(fileService.uploadThumbnailFile(imageName));
                }
            }
        }
        logger.info("Inserting a new board with title: {}", board.getBotitle());
        return bmi.Insert(board);
    }

    // 파일을 저장하고 파일 이름을 반환하는 메소드
    private String saveFile(MultipartFile file) throws IOException {
        return fileService.uploadFile(file);
    }

    // 게시물 조회
    public Board SelectOne(Integer bono){
        logger.info("Fetching board with ID: {}", bono);
        Board board = bmi.SelectOne(bono);
        logger.info("Fetched board: {}", board);
        return board;
    }

    // 게시물 수정
    public int Update(Board board, MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String imageName = saveFile(file);
                if (board.getBoimage01() == null) {
                    board.setBoimage01(imageName);
                    board.setThumb_boimage01(fileService.uploadThumbnailFile(imageName));
                } else if (board.getBoimage02() == null) {
                    board.setBoimage02(imageName);
                    board.setThumb_boimage02(fileService.uploadThumbnailFile(imageName));
                } else if (board.getBoimage03() == null) {
                    board.setBoimage03(imageName);
                    board.setThumb_boimage03(fileService.uploadThumbnailFile(imageName));
                }
            }
        }
        logger.info("Updating board with ID: {}, Title: {}, Images: {}, {}, {}", board.getBono(), board.getBotitle(), board.getBoimage01(), board.getBoimage02(), board.getBoimage03());
        int result = bmi.Update(board);
        logger.info("Update result for board ID {}: {}", board.getBono(), result);
        return result;
    }

    //게시물 삭제
    public int Delete(Integer bono) {
        logger.info("Deleting board with ID: {}", bono);
        int result = bmi.Delete(bono);
        logger.info("Delete result for board ID {}: {}", bono, result);
        return result;
    }

//    // 댓글 리스트
//    public List<BoardDto> getCommentList(BoardDto bdto);
//    // 내가 쓴 글 조회
//    public List<BoardDto> getMypost(BoardDto bdto);
//    // 내가 쓴 글의 개수를 조회
//    public int selectMyPostTotalCount(BoardDto bdto);
//    // 댓글 수 조회
//    public int commentCnt(Long postno);
}
