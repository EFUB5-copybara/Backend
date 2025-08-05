package efub.cpbr.crumble.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //HTTP 요청에 대해 한 번만 실행되는 필터.
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) { // 생성자 추가해 초기화
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String path = request.getRequestURI();

        // 인증이 필요 없는 API 경로들을 건너뜀.
        if (path.startsWith("/auth/login") || path.startsWith("/auth/signup") || path.startsWith("/auth/token")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
        String token = resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        } catch (Exception e) {
            // JWT 관련 에러만 로깅하거나 무시
            System.out.println("JWT 처리 중 예외: " + e.getMessage());
        }


        filterChain.doFilter(request, response);
    }

    // request header에서 토큰값 정보만 겟하는 보조 메서드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
