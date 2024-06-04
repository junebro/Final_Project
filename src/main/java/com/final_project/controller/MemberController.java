package com.final_project.controller;

import com.final_project.Service.CustomUserDetailsService;
import com.final_project.Service.MemberService;
import com.final_project.dto.JwtToken;
import com.final_project.dto.MemberDTO;

import com.final_project.dto.MypageDTO;
import com.final_project.entity.Diary;
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
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public ResponseEntity<?> registerNewMember(@RequestBody MemberDTO memberDTO) {
        System.out.println("Registering new member: " + memberDTO);
        try {
            memberService.registerNewMember(memberDTO);
            // 회원가입 성공 시 클라이언트에 성공 메시지를 반환합니다.
            return ResponseEntity.ok("회원가입에 성공하였습니다.");
        } catch (Exception e) {
            // 서버 측 오류 로깅
            System.err.println("회원 가입 중 에러 발생: " + e.getMessage());
            // 클라이언트에 보다 구체적인 오류 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 처리 중 오류가 발생하였습니다: " + e.getMessage());
        }
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
    public ResponseEntity<?> signIn(@RequestBody MemberDTO member) {

        String username = member.getMemEmail();
        String password = member.getMemPw();

        // 데이터베이스에서 사용자 정보를 불러오기
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 입력된 비밀번호와 데이터베이스에 저장된 암호화된 비밀번호 비교
        String storedPassword = userDetails.getPassword();

        if (passwordEncoder.matches(password, storedPassword)) {
            // Authentication 객체 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            // JwtToken 생성
            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(jwtToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
    }

    private void validateInput(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be null or empty");
        }
    }

    // 내 정보 조회
    @GetMapping(value = "/memInfo/{userNo}")
    public ResponseEntity<?> SelectMem(@PathVariable String userNo) {

        try {
            MemberDTO memInfo = memberService.SelectMem(userNo);
            return ResponseEntity.ok(memInfo); // Map을 JSON 형식으로 반환

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 내 정보 수정
    @PostMapping(value = "/editProfile")
    public ResponseEntity<?> updateDiary(@RequestBody MemberDTO member) {

        System.out.println(member);

        try {
            memberService.Update(member);
            return ResponseEntity.ok("Update successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing your request: " + e.getMessage());
        }
    }

    // 회원 탈퇴
    @DeleteMapping("/members/{userNo}")
    public ResponseEntity<?> deleteMember(@PathVariable String userNo) {
        memberService.DeleteMem(userNo);
        return ResponseEntity.ok().build();
    }

    // 프로필 이미지 업로드
    @PostMapping("/upload-profile-image/{userNo}")
    public String uploadProfileImage(@PathVariable String userNo, @RequestParam("profileImage") MultipartFile file) throws Exception {

        // 현재 시간을 이용해 타임스탬프 생성
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String originalFileName = file.getOriginalFilename();

        // 파일 이름 안전성 확인 및 타임스탬프 추가
        String safeFileName = timestamp + "_" + (originalFileName != null ? Paths.get(originalFileName).getFileName().toString() : "defaultName");

        // 절대 경로 설정
        Path targetLocation = Paths.get("D:/final-project-react/src/images/profileImage/" + safeFileName);

        // 해당 경로의 상위 디렉토리가 없으면 생성
        Files.createDirectories(targetLocation.getParent());

        // 파일 저장
        Files.copy(file.getInputStream(), targetLocation);

        // 사용자 프로필 이미지 경로 업데이트
        memberService.updateUserProfileImage(userNo, safeFileName);

        return safeFileName;
    }

    // 프로필 이미지 조회
//    @GetMapping("/profile-image")
//    public String getProfileImagePath(@PathVariable Long userId) {
//        return memberService.getUserProfileImagePath(userId);
//    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
