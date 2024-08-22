package com.skhu.goormthon.global.jwt;

import com.skhu.goormthon.app.domain.member.Member;
import com.skhu.goormthon.global.exception.CustomException;
import com.skhu.goormthon.global.exception.code.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
public class TokenProvider {

    private final Key key;
    private final long accessTokenValidityTime;
    private final long refreshTokenValidityTime;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.access-token-validity-in-milliseconds}") long accessTokenValidityTime,
                         @Value("${jwt.refresh-token-validity-in-milliseconds}") long refreshTokenValidityTime) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.accessTokenValidityTime = accessTokenValidityTime;
        this.refreshTokenValidityTime = refreshTokenValidityTime;
    }

    private String createToken(Member member, long validityTime) {
        long nowTime = new Date().getTime();
        Date expiryDate = new Date(nowTime + validityTime);

        return Jwts.builder()
                .setSubject(member.getMemberId().toString())
                .claim("Role", member.getRoleType().name())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createAccessToken(Member member) {
        return createToken(member, accessTokenValidityTime);
    }

    public String createRefreshToken(Member member) {
        return createToken(member, refreshTokenValidityTime);
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN_EXCEPTION, ErrorCode.EXPIRED_TOKEN_EXCEPTION.getMessage());
        } catch (SignatureException e) {
            throw new CustomException(ErrorCode.INVALID_SIGNATURE_EXCEPTION, ErrorCode.INVALID_SIGNATURE_EXCEPTION.getMessage());
        }
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        String roleName = claims.get("Role", String.class);

        if (roleName == null) {
            throw new CustomException(ErrorCode.FORBIDDEN_AUTH_EXCEPTION, ErrorCode.FORBIDDEN_AUTH_EXCEPTION.getMessage());
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", Collections.singletonList(authority));
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
