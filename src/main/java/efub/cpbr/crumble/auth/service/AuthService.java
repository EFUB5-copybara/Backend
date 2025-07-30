package efub.cpbr.crumble.auth.service;

import efub.cpbr.crumble.auth.dto.LoginRequestDto;
import efub.cpbr.crumble.auth.dto.SignUpRequestDto;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.jwt.JwtTokenProvider;
import efub.cpbr.crumble.jwt.TokenInfo;
import efub.cpbr.crumble.user.entity.RoleType;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 로직
    public User signup(SignUpRequestDto signUpRequestDto) {
        if(!signUpRequestDto.isPasswordConfirmed()) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        if(userRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        if(userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        User newUser = User.builder()
                .username(signUpRequestDto.getUsername())
                .password(encodedPassword)
                .email(signUpRequestDto.getEmail())
                .nickname(signUpRequestDto.getNickname())
                .role(RoleType.USER) // 기본 역할 USER로 설정
                .point(0) // 초기 포인트 0으로 설정
                .isActive(true) // 계정 활성화 상태로 설정
                .createdAt(LocalDateTime.now()) // 현재 시간으로 생성 시간 설정
                .updatedAt(LocalDateTime.now()) // 현재 시간으로 업데이트 시간 설정
                .build();

        // 사용자 정보 저장
        return userRepository.save(newUser);
    }


    // 로그인 로직
    public TokenInfo login(LoginRequestDto loginRequestDto) {
        log.info("로그인 시도: 사용자명 = {}", loginRequestDto.getUsername());

        // 1. 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        try {
            // 2. AuthenticationManager를 통해 인증 수행
            // 이 시점에서 UserDetailsService.loadUserByUsername()과 PasswordEncoder.matches()가 내부적으로 호출됩니다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            log.info("인증 성공: 사용자명 = {}", authentication.getName());

            // 3. 인증 정보로 JWT 토큰 생성
            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
            log.info("JWT 토큰 생성 완료");

            // 4. 리프레시 토큰 레디스에 저장 (선택 사항, Redis 설정이 올바른지 확인 필요)
            redisTemplate.opsForValue().set("RT:" + authentication.getName(),
                    tokenInfo.getRefreshToken(),
                    jwtTokenProvider.getRefreshTokenExpirationMillis(),
                    TimeUnit.MILLISECONDS);
            log.info("리프레시 토큰 Redis에 저장 완료");

            return tokenInfo;

        } catch (Exception e) {
            // BadCredentialsException, UsernameNotFoundException 등 AuthenticationManager.authenticate()에서
            // 발생할 수 있는 예외를 여기서 잡아서 로그를 남깁니다.
            // 이 예외는 GlobalExceptionHandler에서 처리될 것입니다.
            log.error("로그인 인증 실패: {}", e.getMessage());
            throw e; // 예외를 다시 던져 GlobalExceptionHandler가 잡도록 합니다.
        }
    }
}
