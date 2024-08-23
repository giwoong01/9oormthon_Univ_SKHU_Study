package org.skhu.skhu9roomthonlogin.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skhu.skhu9roomthonlogin.global.jwt.exception.CustomAuthenticationException;
import org.skhu.skhu9roomthonlogin.member.domain.Member;
import org.skhu.skhu9roomthonlogin.member.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenProvider {

    private final MemberRepository memberRepository;

    @Value("${token.expire.time}")
    private long tokenExpireTime;

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성 메서드
    public String generateToken(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpireTime);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 토큰 유효성 검증 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            log.error("유효하지 않은 JWT 토큰입니다: {}", e.getMessage());
            throw new CustomAuthenticationException("유효하지 않은 JWT 토큰입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다: {}", e.getMessage());
            throw new CustomAuthenticationException("만료된 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 비어있습니다: {}", e.getMessage());
            throw new CustomAuthenticationException("JWT 토큰이 비어있습니다.");
        } catch (Exception e) {
            log.error("JWT 토큰 검증 실패: {}", e.getMessage());
            throw new CustomAuthenticationException("JWT 토큰 검증 실패.");
        }
    }

    // 토큰에서 인증 정보 조회 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String email = claims.getSubject();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomAuthenticationException("사용자를 찾을 수 없습니다."));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole().toString()));

        return new UsernamePasswordAuthenticationToken(member.getEmail(), "", authorities);
    }

    // 요청에서 토큰 추출 메서드
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
