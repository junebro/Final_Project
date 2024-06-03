package com.final_project.Service;

import com.final_project.dto.CommentDTO;
import com.final_project.mapper.CommentMapperInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapperInterface cmi;

    // 특정 게시물의 모든 댓글을 조회하는 서비스
    public List<CommentDTO> getCommentsByBono(Integer bono){
        return cmi.selectCommentsByBono(bono);
    }

    // 댓글 추가 서비스
    public CommentDTO addComment(CommentDTO comment) {
        cmi.insertComment(comment);
        return comment;  // 생성된 ID를 포함한 댓글 객체 반환
    }

    // 댓글 내용 업데이트 서비스
    public boolean updateComment(CommentDTO comment) {
        return cmi.updateComment(comment) > 0;
    }

    // 댓글 삭제 서비스
    public boolean deleteComment(Integer comno) {
        return cmi.deleteComment(comno) > 0;
    }

}
