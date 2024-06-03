package com.final_project.controller;

import com.final_project.Service.CommentService;
import com.final_project.dto.CommentDTO;
import com.final_project.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService cs;
    private final MemberService memberService;

    // 특정 게시물의 댓글을 가져오는 API 엔드포인트
    @GetMapping("/comments/{bono}")
    public ResponseEntity<?> getComments(@PathVariable Integer bono) {
        return ResponseEntity.ok(cs.getCommentsByBono(bono));
    }

    // 새 댓글을 생성하는 API 엔드포인트
    @PostMapping("/comments")
    public ResponseEntity<?> createComment(@RequestBody CommentDTO comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();

        // 사용자 ID(username)를 이용하여 memNo 및 memberNick 조회
        Integer memNo = memberService.findMemNoByUserId(username);
        String memberNick = memberService.findMemberNickByUserId(username);

        if (memNo == null || memberNick == null) {
            return ResponseEntity.status(401).body("Invalid user information");
        }

        comment.setMemNo(memNo.toString());
        comment.setMemberNick(memberNick);

        return ResponseEntity.ok(cs.addComment(comment));
    }

    // 댓글을 수정하는 API 엔드포인트
    @PutMapping("/comments")
    public ResponseEntity<?> updateComment(@RequestBody CommentDTO comment) {
        if (cs.updateComment(comment)) {
            return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("댓글 수정에 실패했습니다.");
        }
    }

    // 댓글을 삭제하는 API 엔드포인트
    @DeleteMapping("/comments/{comno}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer comno) {
        if (cs.deleteComment(comno)) {
            return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("댓글 삭제에 실패했습니다.");
        }
    }
}
