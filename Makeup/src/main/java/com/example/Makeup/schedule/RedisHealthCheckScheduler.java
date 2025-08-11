package com.example.Makeup.schedule;

import com.example.Makeup.service.common.CacheCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisHealthCheckScheduler {

  private final CacheCategoryService cacheCategoryService;

  @Scheduled(fixedRate = 60000) // 1 minutes
  public void checkRedis() {
    cacheCategoryService.checkRedisConnection();
  }
}
