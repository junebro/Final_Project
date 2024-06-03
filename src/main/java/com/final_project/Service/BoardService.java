package com.final_project.Service;

import com.final_project.dto.BoardDTO;
import com.final_project.mapper.BoardMapperInterface;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Page<BoardDTO> SelectAll(Pageable pageable, String orderBy) {
        logger.info("Starting pagination with page number: {} and page size: {}", pageable.getPageNumber(), pageable.getPageSize());
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<BoardDTO> boards = bmi.SelectAll(orderBy); // 실제 데이터 조회를 BoardDTO로 변경
        long total = countBoards(); // 전체 게시물 수 조회
        logger.info("Fetched {} boards with total of {} entries", boards.size(), total);
        return new PageImpl<>(boards, pageable, total);
    }

    // 전체 게시물 수 조회
    private long countBoards() {
        long count = bmi.count();
        logger.info("Total number of boards: {}", count);
        return count; // 전체 게시물 수를 반환하는 MyBatis 메소드
    }

    // 일반 리스트 조회
    public List<BoardDTO> SelectAll(String orderBy) {
        logger.info("Fetching all boards ordered by: {}", orderBy);
        return bmi.SelectAll(orderBy);
    }

    // 게시물 검색
    public List<BoardDTO> searchByTitleOrContent(String keyword) {
        logger.info("Searching boards with keyword: {}", keyword);
        List<BoardDTO> searchResults = bmi.searchByTitleOrContent(keyword);
        logger.info("Found {} boards matching the keyword: {}", searchResults.size(), keyword);
        return searchResults;
    }

    // 게시물 등록
    public int Insert(BoardDTO boardDto, MultipartFile[] files) throws IOException {
        // 이미지 파일 저장 및 파일명 설정
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String imageName = saveFile(file);
                if (boardDto.getBoimage01() == null) {
                    boardDto.setBoimage01(imageName);
                    boardDto.setThumb_boimage01(fileService.uploadThumbnailFile(imageName));
                } else if (boardDto.getBoimage02() == null) {
                    boardDto.setBoimage02(imageName);
                    boardDto.setThumb_boimage02(fileService.uploadThumbnailFile(imageName));
                } else if (boardDto.getBoimage03() == null) {
                    boardDto.setBoimage03(imageName);
                    boardDto.setThumb_boimage03(fileService.uploadThumbnailFile(imageName));
                }
            }
        }
        logger.info("Inserting a new board with title: {}", boardDto.getBotitle());
        return bmi.Insert(boardDto);
    }

    // 파일을 저장하고 파일 이름을 반환하는 메소드
    private String saveFile(MultipartFile file) throws IOException {
        return fileService.uploadFile(file);
    }

    // 게시물 조회
    public BoardDTO SelectOne(Integer bono) {
        logger.info("Fetching board with ID: {}", bono);
        BoardDTO board = bmi.SelectOne(bono);
        logger.info("Fetched board: {}", board);
        return board;
    }

    // 게시물 수정
    public int Update(BoardDTO boardDto, MultipartFile[] files) throws IOException {
        // 이미지 파일 처리
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String imageName = saveFile(file);
                if (boardDto.getBoimage01() == null) {
                    boardDto.setBoimage01(imageName);
                    boardDto.setThumb_boimage01(fileService.uploadThumbnailFile(imageName));
                } else if (boardDto.getBoimage02() == null) {
                    boardDto.setBoimage02(imageName);
                    boardDto.setThumb_boimage02(fileService.uploadThumbnailFile(imageName));
                } else if (boardDto.getBoimage03() == null) {
                    boardDto.setBoimage03(imageName);
                    boardDto.setThumb_boimage03(fileService.uploadThumbnailFile(imageName));
                }
            }
        }
        logger.info("Updating board with ID: {}, Title: {}, Images: {}, {}, {}", boardDto.getBono(), boardDto.getBotitle(), boardDto.getBoimage01(), boardDto.getBoimage02(), boardDto.getBoimage03());
        int result = bmi.Update(boardDto);
        logger.info("Update result for board ID {}: {}", boardDto.getBono(), result);
        return result;
    }

    //게시물 삭제
    public int Delete(Integer bono) {
        logger.info("Deleting board with ID: {}", bono);
        int result = bmi.Delete(bono);
        logger.info("Delete result for board ID {}: {}", bono, result);
        return result;
    }

    // 조회수 업데이트 메소드
    @Transactional
    public void updateViewCount(Integer bono) {
        int result = bmi.updateViewCount(bono);
        if (result == 0) {
            logger.error("Failed to update view count for bono: {}", bono);
        } else {
            logger.info("Successfully updated view count for bono: {}", bono);
        }
    }

    // 좋아요 상태 체크
    public boolean checkLike(Integer bono, Integer memNo) {
        return bmi.checkLike(bono, memNo) > 0;
    }

    // 좋아요 추가
    @Transactional
    public boolean addLike(Integer bono, Integer memNo) {
        return bmi.addLike(bono, memNo) > 0;
    }

    // 좋아요 취소
    @Transactional
    public boolean removeLike(Integer bono, Integer memNo) {
        return bmi.removeLike(bono, memNo) > 0;
    }
}
