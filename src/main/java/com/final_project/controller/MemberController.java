package com.final_project.controller;

import com.final_project.Service.MemberService;
import com.final_project.dto.MemberDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/join")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/join")
    public String showRegisterForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "join";
    }

    @PostMapping("/member/join")
//    public String registerNewMember(@RequestBody MemberDTO memberDTO) {
    public ResponseEntity<?> registerNewMember(@RequestBody MemberDTO memberDTO) {
        System.out.println(memberDTO);
        try {
            memberService.registerNewMember(memberDTO);
            //return "redirect:/login"; // 회원가입 성공 시 로그인 페이지로 리다이렉트
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터를 가져오는 데 실패했습니다.");

        }
        return null;
    }
    // 준형 수정
    // 이메일 중복 체크
    @GetMapping("/check/email")
    public ResponseEntity<Boolean> checkEmailAvailability(@RequestParam String newEmail) {
        System.out.println(newEmail);
        boolean isAvailable = memberService.isEmailAvailable(newEmail);
        return ResponseEntity.ok(isAvailable);  // HTTP 200 상태 코드와 함께 결과 반환
    }

    // 닉네임 중복 체크
    @GetMapping("/check/nick")
    public ResponseEntity<Boolean> checkNickAvailability(@RequestParam String newNick) {
        boolean isAvailable = memberService.isNickAvailable(newNick);
        return ResponseEntity.ok(isAvailable);
    }
}
