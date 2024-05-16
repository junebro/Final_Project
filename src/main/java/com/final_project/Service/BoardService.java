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

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapperInterface bmi;
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

    public int Insert(Board board) {
        logger.info("Inserting a new board with title: {}", board.getBotitle());
        int result = bmi.Insert(board);
        logger.info("Insert result: {}", result);
        return result;
    }

    public Board SelectOne(Integer bono){
        logger.info("Fetching board with ID: {}", bono);
        Board board = bmi.SelectOne(bono);
        logger.info("Fetched board: {}", board);
        return board;
    }

    public int Update(Board board) {
        logger.info("Updating board with ID: {}", board.getBono());
        int result = bmi.Update(board);
        logger.info("Update result for board ID {}: {}", board.getBono(), result);
        return result;
    }

    public int Delete(Integer bono) {
        logger.info("Deleting board with ID: {}", bono);
        int result = bmi.Delete(bono);
        logger.info("Delete result for board ID {}: {}", bono, result);
        return result;
    }
}
