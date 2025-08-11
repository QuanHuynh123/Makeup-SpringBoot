package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.StaffServiceImpl;
import com.example.Makeup.utils.RedisStatusManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheStaffService {

  private static final String STAFF_CACHE_KEY = "staffs";
  private final RedisTemplate<String, Object> redisTemplate;
  private final StaffServiceImpl staffServiceImpl;

  public ApiResponse<List<StaffDTO>> cacheAllStaff() {
    if (!RedisStatusManager.isRedisAvailable()) {
      return staffServiceImpl.getAllStaff();
    }
    try {
      Object cached = redisTemplate.opsForValue().get(STAFF_CACHE_KEY);
      if (cached instanceof List<?> list && !list.isEmpty()) {
        return ApiResponse.success("Staff list (from cache)", (List<StaffDTO>) list);
      }
    } catch (RedisConnectionFailureException e) {
      log.warn("⚠️ Redis connection failed (staff): {}", e.getMessage());
      RedisStatusManager.setRedisAvailable(false);
    } catch (Exception e) {
      log.warn("⚠️ Redis GET failed (staff), fallback to DB: {}", e.getMessage());
    }

    return staffServiceImpl.getAllStaff();
  }
}
