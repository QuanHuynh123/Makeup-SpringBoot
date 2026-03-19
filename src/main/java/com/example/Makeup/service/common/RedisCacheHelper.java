package com.example.Makeup.service.common;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisCacheHelper {

  private final RedisTemplate<String, Object> redisTemplate;

  @SuppressWarnings("unchecked")
  public <T> List<T> getOrLoadList(
      String key, Duration ttl, Supplier<List<T>> dbSupplier, String label) {
    try {
      Object cached = redisTemplate.opsForValue().get(key);
      if (cached instanceof List<?> list) {
        log.debug("Cache hit for {} with key={}", label, key);
        return (List<T>) list;
      }
    } catch (Exception e) {
      log.warn("Redis GET failed for {} key={}. Fallback to DB: {}", label, key, e.getMessage());
    }

    List<T> fromDb = dbSupplier.get();

    try {
      redisTemplate.opsForValue().set(key, fromDb, ttl);
      log.debug("Cached {} with key={}", label, key);
    } catch (Exception e) {
      log.warn("Redis SET failed for {} key={}. Returning DB result: {}", label, key, e.getMessage());
    }

    return fromDb;
  }

  public void evict(String... keys) {
    try {
      redisTemplate.delete(Arrays.asList(keys));
      log.debug("Evicted cache keys: {}", Arrays.toString(keys));
    } catch (Exception e) {
      log.warn("Redis EVICT failed for keys {}: {}", Arrays.toString(keys), e.getMessage());
    }
  }
}