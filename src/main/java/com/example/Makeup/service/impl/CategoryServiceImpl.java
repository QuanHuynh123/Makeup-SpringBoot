package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.CategoryDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.mapper.CategoryMapper;
import com.example.Makeup.repository.CategoryRepository;
import com.example.Makeup.service.ICategoryService;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  private static final String CATEGORY_CACHE_KEY = "categories";

  @Override
  public ApiResponse<List<CategoryDTO>> getAllCategory() {
    List<CategoryDTO> result =
        categoryRepository.findAll().stream().map(categoryMapper::toCategoryDTO).toList();

    try {
      redisTemplate.opsForValue().set(CATEGORY_CACHE_KEY, result, Duration.ofMinutes(30));
    } catch (Exception e) {
      System.out.println("⚠️ Redis SET failed: " + e.getMessage());
    }

    return ApiResponse.success("Get all categories success (from DB)", result);
  }

  public void invalidateCategoryCache() {
    try {
      redisTemplate.delete(CATEGORY_CACHE_KEY);
    } catch (Exception e) {
      System.out.println("⚠️ Redis DEL failed: " + e.getMessage());
    }
  }
}
