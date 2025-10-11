package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.CategoryDTO;
import com.example.Makeup.service.impl.CategoryServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheCategoryService {

  private static final String CATEGORY_CACHE_KEY = "categories";
  private final RedisTemplate<String, Object> redisTemplate;
  private final CategoryServiceImpl categoryServiceImpl;

  public List<CategoryDTO> cacheAllCategory() {
    try {
      Object cached = redisTemplate.opsForValue().get(CATEGORY_CACHE_KEY);
      if (cached instanceof List<?> list && !list.isEmpty()) {
        log.debug("Categories loaded from Redis cache");
        return (List<CategoryDTO>) list;
      }
    } catch (Exception e) {
      log.warn("⚠️ Redis GET failed (categories), fallback to DB: {}", e.getMessage());
    }

    List<CategoryDTO> fromDb = categoryServiceImpl.getAllCategory();
    try {
      redisTemplate.opsForValue().set(CATEGORY_CACHE_KEY, fromDb);
      log.debug("Categories cached to Redis");
    } catch (Exception e) {
      log.warn("⚠️ Failed to cache categories: {}", e.getMessage());
    }

    return fromDb;
  }
}

