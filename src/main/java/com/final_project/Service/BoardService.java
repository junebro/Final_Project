package com.final_project.Service;

import com.final_project.entity.Board;
import com.final_project.mapper.BoardMapperInterface;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapperInterface bmi;

    // 페이징 처리된 리스트 조회
    public Page<Board> SelectAll(Pageable pageable){

        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<Board> boards = bmi.SelectAll();// 실제 데이터 조회
        long total = countBoards(); /// 전체 게시물 수 조회

        return new PageImpl<>(boards, pageable, total);
    }

    // 전체 게시물 수 조회
    private long countBoards() {
        return bmi.count(); // 전체 게시물 수를 반환하는 MyBatis 메소드
    }

    public List<Board> SelectAll(){
        return bmi.SelectAll();
    }

    public int Insert(Board board) {
        return bmi.Insert(board);
    }

    public Board SelectOne(Integer bono){
        return bmi.SelectOne(bono);
    }

    public int Update(Board board) {
        return bmi.Update(board);
    }

    public int Delete(Integer bono) {
        return bmi.Delete(bono);
    }

}
