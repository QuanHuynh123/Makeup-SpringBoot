package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.TypeMakeupDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.TypeMakeupServiceImpl;
import com.example.Makeup.utils.RedisStatusManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheTypeMakeupService {

    private static final String SERVICE_MAKEUP_CACHE_KEY = "type-makeup";
    private final RedisTemplate<String, Object> redisTemplate;
    private final TypeMakeupServiceImpl typeMakeupServiceImpl;

    public ApiResponse<List<TypeMakeupDTO>> cacheAllTypeMakeup() {
        if (!RedisStatusManager.isRedisAvailable()) {
            return typeMakeupServiceImpl.getAllTypeMakeup();
        }
        try {
            Object cached = redisTemplate.opsForValue().get(SERVICE_MAKEUP_CACHE_KEY);
            if (cached instanceof List<?> list && !list.isEmpty()) {
                ApiResponse.success("Services list (from cache)",(List<TypeMakeupDTO>) list);
            }
        } catch (RedisConnectionFailureException e) {
            log.warn("⚠️ Redis connection failed (type makeup): {}", e.getMessage());
            RedisStatusManager.setRedisAvailable(false);
        } catch (Exception e) {
            log.warn("⚠️ Redis GET failed (type makeup), fallback to DB: {}", e.getMessage());
        }
        return typeMakeupServiceImpl.getAllTypeMakeup();
    }
}
