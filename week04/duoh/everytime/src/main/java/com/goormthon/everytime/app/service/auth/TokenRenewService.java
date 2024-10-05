package com.goormthon.everytime.app.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenRenewService {

    private static final String REFRESH_TOKEN_PREFIX = "refreshToken:";

    private final RedisTemplate<String, String> redisTemplate;

    public void saveRefreshToken(String refreshToken, Long userId) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        redisTemplate.delete(key);
        redisTemplate.opsForValue().set(key, refreshToken, 7, TimeUnit.DAYS);
    }
}
