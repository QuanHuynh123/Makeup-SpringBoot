package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.TypeMakeupDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.TypeMakeupServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheTypeMakeupService {

  private static final String SERVICE_MAKEUP_CACHE_KEY = "type-makeup";
  private final RedisTemplate<String, Object> redisTemplate;
  private final TypeMakeupServiceImpl typeMakeupServiceImpl;

  public ApiResponse<List<TypeMakeupDTO>> cacheAllTypeMakeup() {
    try {
      Object cached = redisTemplate.opsForValue().get(SERVICE_MAKEUP_CACHE_KEY);
      if (cached instanceof List<?> list && !list.isEmpty()) {
        return ApiResponse.success("Services list (from cache)", (List<TypeMakeupDTO>) list);
      }
    } catch (Exception e) {
      log.warn("⚠️ Redis error, fallback to DB: {}", e.getMessage());
    }

    List<TypeMakeupDTO> fromDb = typeMakeupServiceImpl.getAllTypeMakeup();

    try {
      redisTemplate.opsForValue().set(SERVICE_MAKEUP_CACHE_KEY, fromDb);
    } catch (Exception e) {
      log.warn("⚠️ Failed to write cache: {}", e.getMessage());
    }

    return ApiResponse.success("Get all services success (from DB)", fromDb);
  }
}
