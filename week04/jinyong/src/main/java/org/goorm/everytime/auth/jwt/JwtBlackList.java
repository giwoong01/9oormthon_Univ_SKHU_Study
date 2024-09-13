package org.goorm.everytime.auth.jwt;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class JwtBlackList {

    private static final String REFRESH_BLACKLIST_PREFIX = "refresh_blacklist:";
    private final RedisTemplate<String, Object> redisTemplate;

    public JwtBlackList(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void blacklistToken(String token, Duration duration) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(REFRESH_BLACKLIST_PREFIX + token, true, duration);
    }

    public boolean isRefreshTokenBlacklisted(String refreshToken) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Boolean isBlacklisted = (Boolean) ops.get(REFRESH_BLACKLIST_PREFIX + refreshToken);
        return isBlacklisted != null && isBlacklisted;
    }
}
