package com.example.Makeup.service.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateLimitService {

    private final StringRedisTemplate stringRedisTemplate;

    public boolean isRateLimited(String key, int limit, Duration windowDuration) {
        try {
            String redisKey = "rate_limit:" + key;
            Long currentCount = stringRedisTemplate.opsForValue().increment(redisKey);

            if (currentCount != null && currentCount == 1) {
                stringRedisTemplate.expire(redisKey, windowDuration);
            }

            boolean limited = currentCount != null && currentCount > limit;

            if (limited) {
                log.warn("Rate limit exceeded for key:{}, count: {}", key, currentCount);
            }
            return limited;
        }catch (Exception e) {
            log.error("Error checking rate limit for key: {}", key, e);
            return false; // In case of error, do not limit
        }
    }
}
