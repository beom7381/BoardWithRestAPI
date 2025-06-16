package com.toy.project.authorize.service;

import com.toy.project.authorize.constant.CONSTATNS;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveRefreshToken(String userId, String refreshToken, long expirySecond) {
        redisTemplate.opsForValue().set(
                CONSTATNS.REFRESH_TOKEN_PREFIX + userId,
                refreshToken,
                Duration.ofSeconds(expirySecond)
        );
    }

    public String getRefreshToken(String userId) {
        return redisTemplate.opsForValue().get(
                CONSTATNS.REFRESH_TOKEN_PREFIX + userId
        );
    }

    public void deleteRefreshToken(String userId) {
        redisTemplate.delete(
                CONSTATNS.REFRESH_TOKEN_PREFIX + userId
        );
    }

    public void blockAccessToken(String accessToken, long expirySecond) {
        redisTemplate.opsForValue().set(
                CONSTATNS.BLOCK_ACCESS_TOKEN_PREFIX + accessToken,
                "Sign Out",
                Duration.ofSeconds(expirySecond)
        );
    }

    public boolean hasRefreshToken(String userId){
        return redisTemplate.hasKey(
                CONSTATNS.REFRESH_TOKEN_PREFIX + userId
        );
    }

    public boolean isAccessTokenBlocked(String accessToken) {
        return redisTemplate.hasKey(
                CONSTATNS.BLOCK_ACCESS_TOKEN_PREFIX + accessToken
        );
    }
}
