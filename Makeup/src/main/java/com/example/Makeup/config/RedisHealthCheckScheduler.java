package com.example.Makeup.config;

import com.example.Makeup.service.common.CacheCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisHealthCheckScheduler {

    private final CacheCategoryService cacheCategoryService;

    @Scheduled(fixedRate = 6000000) // 100 minutes
    public void checkRedis() {
        cacheCategoryService.checkRedisConnection();
    }
}
