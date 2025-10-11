package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.service.impl.StaffServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheStaffService {

  private static final String STAFF_CACHE_KEY = "staffs";
  private final RedisTemplate<String, Object> redisTemplate;
  private final StaffServiceImpl staffServiceImpl;

  public List<StaffDTO> cacheAllStaff() {
    try {
      List<StaffDTO> cached = (List<StaffDTO>) redisTemplate.opsForValue().get(STAFF_CACHE_KEY);
      if (cached != null && !cached.isEmpty()) {
        log.info("Get staff list from Redis cache");
        return cached;
      }
    } catch (Exception e) {
      log.warn("⚠️ Redis fail, fallback DB: {}", e.getMessage());
    }

    List<StaffDTO> staffList = staffServiceImpl.getAllStaff();

    try {
      redisTemplate.opsForValue().set(STAFF_CACHE_KEY, staffList);
      log.info("Staff list cached to Redis");
    } catch (Exception e) {
      log.warn("⚠️ Redis set fail: {}", e.getMessage());
    }

    return staffList;
  }
}

