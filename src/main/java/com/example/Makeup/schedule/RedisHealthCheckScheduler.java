package com.example.Makeup.schedule;

import com.example.Makeup.service.common.CacheCategoryService;
import com.example.Makeup.service.common.RedisHealthCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisHealthCheckScheduler {

  private final RedisHealthCheckService redisHealthCheckService;

  @Scheduled(fixedRate = 60000) // 1 minutes
  public void checkRedis() {
    redisHealthCheckService.checkConnection();
  }
}
