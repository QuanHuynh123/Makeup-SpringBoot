package com.example.Makeup.service.common;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateLimitService {

  private final StringRedisTemplate stringRedisTemplate;

  public boolean isRateLimited(String key, int limit, Duration windowDuration) {
    try {
      String redisKey = "rate_limit:" + key;
      Long count = stringRedisTemplate.opsForValue().increment(redisKey);
      if (count != null && count == 1) {
        // First request in this window — set the expiry
        stringRedisTemplate.expire(redisKey, windowDuration);
      }
      if (count != null && count > limit) {
        log.warn("Rate limit exceeded for key: {}, count: {}", key, count);
        return true;
      }
      return false;
    } catch (Exception e) {
      log.error("Error checking rate limit for key: {}", key, e);
      return false; // Redis unavailable — allow request
    }
  }

}
