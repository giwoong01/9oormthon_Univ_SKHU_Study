package com.example.loginstudy.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    private final SecretKey secretKey;

    private static final String CLAIM_USERNAME = "username";
    private static final String CLAIM_ROLE = "role";

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        byte[] decodedKey = Base64.getUrlDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(CLAIM_USERNAME, String.class);
    }

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(CLAIM_ROLE, String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String createJwt(String username, String role, Long expiredMs) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claim(CLAIM_USERNAME, username)
                .claim(CLAIM_ROLE, role)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
