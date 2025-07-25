package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.CategoryDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.CategoryServiceImpl;
import com.example.Makeup.utils.RedisStatusManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheCategoryService {

    private static final String CATEGORY_CACHE_KEY = "categories";
    private final RedisTemplate<String, Object> redisTemplate;
    private final CategoryServiceImpl categoryServiceImpl;

    public ApiResponse<List<CategoryDTO>> cacheAllCategory() {
        if (!RedisStatusManager.isRedisAvailable()) {
            log.debug("⚠️ Redis is not available, falling back to DB");
            return categoryServiceImpl.getAllCategory();
        }

        try {
            Object cached = redisTemplate.opsForValue().get(CATEGORY_CACHE_KEY);
            if (cached instanceof List<?> list && !list.isEmpty()) {
                return ApiResponse.success("Category list (from cache)", (List<CategoryDTO>) list);
            }
        } catch (RedisConnectionFailureException e) {
            log.warn("⚠️ Redis connection failed: {}", e.getMessage());
            RedisStatusManager.setRedisAvailable(false);
        } catch (Exception e) {
            log.warn("⚠️ Redis GET failed, fallback to DB: {}", e.getMessage());
        }

        return categoryServiceImpl.getAllCategory();
    }


    public void checkRedisConnection() {
        try {
            assert redisTemplate.getConnectionFactory() != null;
            redisTemplate.getConnectionFactory().getConnection().ping();
            if (!RedisStatusManager.isRedisAvailable()) {
                log.info("✅ Redis connection restored");
                RedisStatusManager.setRedisAvailable(true);
            }
        } catch (RedisConnectionFailureException e) {
            log.warn("⚠️ Redis connection check failed: {}", e.getMessage());
            RedisStatusManager.setRedisAvailable(false);
        }
    }

}
