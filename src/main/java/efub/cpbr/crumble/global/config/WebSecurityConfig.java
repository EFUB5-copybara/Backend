package efub.cpbr.crumble.global.config;

//import efub.cpbr.crumble.auth.service.UserDetailsServiceImpl; // 사용자 정보 로드 서비스
import efub.cpbr.crumble.jwt.JwtAuthenticationFilter; // JWT 필터 (지금은 주석 처리)
import efub.cpbr.crumble.jwt.JwtTokenProvider; // JWT 토큰 생성/검증 도구
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // ⭐ AuthenticationManager 가져오는 핵심 클래스
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    //private final UserDetailsServiceImpl userDetailsService;
    //private final JwtTokenProvider jwtTokenProvider; // JWT 필터에 필요 (지금은 주석)

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안함
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/auth/signup", // 회원가입 허용
                                "/auth/login",  // 로그인 허용
                                "/auth/token",  // 토큰 재발급 허용 (향후 구현 시)
                                "/swagger-ui/**", // Swagger UI 접근 허용
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll() // 위에 명시된 경로들은 인증 없이 접근 허용
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )

        // 로그인 테스트가 성공하면 그때 활성화하여 테스트
        // .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}