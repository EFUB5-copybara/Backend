package efub.cpbr.crumble.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey key; // JWT 서명에 사용될 비밀 키
    private final UserDetailsService userDetailsService;

    // Access Token 만료 시간 (밀리초)
    @Value("${jwt.access-token-expiration-millis}")
    private long accessTokenExpirationMillis;

    // Refresh Token 만료 시간 (밀리초)
    @Value("${jwt.refresh-token-expiration-millis}")
    private long refreshTokenExpirationMillis;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Base64로 인코딩된 secretKey 디코딩
        this.key = Keys.hmacShaKeyFor(keyBytes); // HMAC SHA 키 생성
    }

    // 유저 정보를 통해 Access Token, Refresh Token 생성
    public TokenInfo generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) //권한 문자열 추출
                .collect(Collectors.joining(",")); // 콤마 문자열로 변환

        long now = (new Date()).getTime(); // 현재 시간
        // Access Token 생성
        // 만료 시간: 30분
        Date accessTokenExpiresIn = new Date(now + accessTokenExpirationMillis);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256) // Access Token 서명 추가
                .compact(); // 문자열 형태로 압축해 반환

        // Refresh Token 생성
        // 만료 시간: 7일
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + refreshTokenExpirationMillis))
                .signWith(key, SignatureAlgorithm.HS256) // Refresh Token 서명 추가
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    // 토큰 안에 있는 사용자 이름과 권한 정보를 추출
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기 - GrantedAuthority 객체로 변환
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());


        // 스프링 시큐리티 User 생성 시 비밀번호는 "" (빈 문자열)로 설정
        UserDetails principal = userDetailsService.loadUserByUsername(claims.getSubject());

        // Authentication return
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 유효성 검증
    // 만약 토큰이 손상되었거나, 만료되었거나, 지원하지 않는 형식이라면 false를 반환
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true; // 유효한 토큰 !!
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) { // 만료
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) { // 지원되지 않는 형식
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    // private 메서드 - Access Token을 복호화하여 Claims(토큰 내용물)를 추출
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            // 토큰이 만료되었더라도 클레임은 가져올 수 있도록
            return e.getClaims(); // 만료된 토큰의 경우 .getClaims() 사용
        }
    }

    public long getRefreshTokenExpirationMillis() {
        return refreshTokenExpirationMillis;
    }
}