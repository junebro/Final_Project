package com.final_project.controller;

import com.final_project.Service.CustomUserDetailsService;
import com.final_project.Service.MemberService;
import com.final_project.dto.JwtToken;
import com.final_project.dto.MemberDTO;
import com.final_project.entity.Member;
import com.final_project.utility.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@RestController
@RequestMapping("/join")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenProvider getUserIdFromToken;
    @Autowired
    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder, CustomUserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider, JwtTokenProvider getUserIdFromToken) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.getUserIdFromToken = getUserIdFromToken;
    }



    @GetMapping("/member/join")
    public String showRegisterForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "join";
    }

    @PostMapping("/member/join")
//  public String registerNewMember(@RequestBody MemberDTO memberDTO) {
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




    @PostMapping("/member/login")
    public JwtToken signIn(@RequestBody Member member) {

        String username = member.getMemEmail();
        String password = member.getMemPw();

        // 데이터베이스에서 사용자 정보를 불러오기
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 입력된 비밀번호와 데이터베이스에 저장된 암호화된 비밀번호 비교
        String storedPassword = userDetails.getPassword();
        System.out.println("입력된 비밀번호: " + password);
        System.out.println("저장된 암호화된 비밀번호: " + storedPassword);

        if (passwordEncoder.matches(password, storedPassword)) {
            // Authentication 객체 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // JwtToken 생성
            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

//            String tokenString = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMSIsImF1dGgiOiIiL…zMTV9.VKtAf_fWJogGaSTMfDaaJ2HjX54PMMmTHqE5EgAaopg";
//
//            // 추출한 토큰을 사용하여 사용자 ID를 추출합니다.
//            String bb = jwtTokenProvider.getUserIdFromToken(tokenString);
//            System.out.println("토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰");
//            System.out.println(bb);
//            System.out.println("토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰토큰");

            return jwtToken;
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }

    private void validateInput(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be null or empty");
        }
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
