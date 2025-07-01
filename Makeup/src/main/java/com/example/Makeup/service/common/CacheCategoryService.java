package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.CategoryDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheCategoryService {

    private static final String CATEGORY_CACHE_KEY = "categories";
    private final RedisTemplate<String, Object> redisTemplate;
    private final CategoryService categoryService;

    public ApiResponse<List<CategoryDTO>> cacheAllCategory() {
        try {
            Object cached = redisTemplate.opsForValue().get(CATEGORY_CACHE_KEY);
            if (cached instanceof List<?> list && !list.isEmpty()) {
                return ApiResponse.success("Category list (from cache)", (List<CategoryDTO>) list);
            }
        } catch (Exception e) {
            log.warn("⚠️ Redis GET failed, fallback to DB: " + e.getMessage());
        }

        return categoryService.getAllCategory();
    }

}
