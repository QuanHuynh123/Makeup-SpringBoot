package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.TypeMakeupDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.TypeMakeupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheTypeMakeupService {

    private static final String SERVICE_MAKEUP_CACHE_KEY = "type-makeup";
    private final RedisTemplate<String, Object> redisTemplate;
    private final TypeMakeupService typeMakeupService;

    public ApiResponse<List<TypeMakeupDTO>> cacheAllTypeMakeup() {
        try {
            Object cached = redisTemplate.opsForValue().get(SERVICE_MAKEUP_CACHE_KEY);
            if (cached instanceof List<?> list && !list.isEmpty()) {
                ApiResponse.success("Services list (from cache)",(List<TypeMakeupDTO>) list);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Redis GET failed: " + e.getMessage());
        }

        return typeMakeupService.getAllTypeMakeup();
    }
}
