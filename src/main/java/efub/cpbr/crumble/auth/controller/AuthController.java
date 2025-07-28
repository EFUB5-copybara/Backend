package efub.cpbr.crumble.auth.controller;

import efub.cpbr.crumble.auth.dto.LoginRequestDto;
import efub.cpbr.crumble.auth.dto.SignUpRequestDto;
import efub.cpbr.crumble.auth.service.AuthService;
import efub.cpbr.crumble.jwt.TokenInfo;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid; // Spring Boot 3.x에서 Validation 사용 시 필요

@RestController // 이 클래스가 REST API 컨트롤러임을 나타냅니다.
@RequestMapping("/auth") // 이 컨트롤러의 모든 메서드는 "/auth" 경로로 시작합니다.
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 만들어 줍니다.
public class AuthController {

    private final AuthService authService;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        User user = authService.signup(signUpRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공: " + user.getUsername());
    }

    // 로그인 API
//    @PostMapping("/login")
//    public TokenInfo login(@Valid @RequestBody LoginRequestDto request) {
//        return authService.login(request);
//    }
}