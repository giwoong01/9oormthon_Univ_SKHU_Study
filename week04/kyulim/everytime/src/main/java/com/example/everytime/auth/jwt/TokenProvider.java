package com.example.everytime.auth.jwt;

import com.example.everytime.auth.dto.TokenDto;
import com.example.everytime.auth.jwt.exception.CustomAuthenticationException;
import com.example.everytime.member.domain.Member;
import com.example.everytime.member.domain.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenProvider {

    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access_expiration_time}")
    private String accessTokenExpTime;

    @Value("${jwt.refresh_expiration_time}")
    private String refreshTokenExpTime;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] key = Decoders.BASE64URL.decode(secret); // secret을 복호화해서 key 배열에 저장
        this.key = Keys.hmacShaKeyFor(key); // key 배열 -> HMAC SHA 알고리즘에 맞는 암호화 키 객체 생성
    }

    // accessToken
    public TokenDto createAccessToken(String id) {
        Date nowTime = new Date();
        Date accessTokenExpiration = new Date(nowTime.getTime() + Long.parseLong(accessTokenExpTime));

        String accessToken = Jwts.builder()
                .setSubject(id)
                .setIssuedAt(nowTime)
                .setExpiration(accessTokenExpiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    // accessToken & refreshToken
    public TokenDto generateToken(String id) {
        Date nowTime = new Date();
        Date accessTokenExpiration = new Date(nowTime.getTime() + Long.parseLong(accessTokenExpTime));
        Date refreshTokenExpiration = new Date(nowTime.getTime() + Long.parseLong(refreshTokenExpTime));

        String accessToken = Jwts.builder()
                .setSubject(id)
                .setIssuedAt(nowTime)
                .setExpiration(accessTokenExpiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setIssuedAt(nowTime)
                .setExpiration(refreshTokenExpiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Member member = memberRepository.findByEmail(claims.getSubject()).
                orElseThrow();

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole().toString()));
        return new UsernamePasswordAuthenticationToken(member.getEmail(),
                "", authorities);
    }

    // JWT 토큰의 유효성을 검사하고, 유효한 경우 Claims를 반환
//    private Claims parseClaims(String token) {
//        try {
//            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//        } catch (ExpiredJwtException e) {
//            return e.getClaims();
//        }
//    }

    // 토큰 검증
    public boolean validateToken(String token) throws CustomAuthenticationException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException | MalformedJwtException exception) {
            log.error("JWT가 유효하지 않습니다.");
            throw new CustomAuthenticationException("JWT가 유효하지 않습니다.");
        } catch (SignatureException exception) {
            log.error("JWT 서명 검증에 실패했습니다.");
            throw new CustomAuthenticationException("JWT 서명 검증에 실패했습니다.");
        } catch (ExpiredJwtException exception) {
            log.error("JWT가 만료되었습니다.");
            throw new CustomAuthenticationException("JWT가 만료되었습니다.");
        } catch (IllegalArgumentException exception) {
            log.error("JWT가 null이거나 비어 있거나 공백만 있습니다.");
            throw new CustomAuthenticationException("JWT가 null이거나 비어 있거나 공백만 있습니다.");
        } catch (Exception exception) {
            log.error("JWT 검증에 실패했습니다.", exception);
            throw new CustomAuthenticationException("JWT 검증에 실패했습니다.");
        }
    }
}
