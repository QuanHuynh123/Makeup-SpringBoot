package com.example.Makeup.exception;

import com.example.Makeup.utils.RedisStatusManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomCacheErrorHandler implements CacheErrorHandler {

    private final RedisStatusManager redisStatusManager;

    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
        handleError(e, "GET", key);
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
        handleError(e, "PUT", key);
    }

    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
        handleError(e, "EVICT", key);
    }

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        handleError(e, "CLEAR", null);
    }

    private void handleError(RuntimeException e, String operation, Object key) {
        log.warn("Cache {} error for key {}: {}", operation, key != null ? key : "N/A", e.getMessage());
        if (e instanceof RedisConnectionFailureException) {
            log.error("⚠ Redis connection failure detected!");
            redisStatusManager.setRedisAvailable(false);
        }
    }
}