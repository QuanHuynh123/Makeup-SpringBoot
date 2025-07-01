package com.example.Makeup.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
        log.warn("Redis GET error for key: {}", key, e);
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
        log.warn("Redis PUT error for key: {}", key, e);
    }

    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
        log.warn("Redis EVICT error for key: {}", key, e);
    }

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        log.warn("Redis CLEAR error", e);
    }
}
