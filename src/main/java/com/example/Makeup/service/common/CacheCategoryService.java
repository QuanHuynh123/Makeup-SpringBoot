package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.CategoryDTO;
import com.example.Makeup.service.impl.CategoryServiceImpl;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheCategoryService {

  private static final Duration CACHE_TTL = Duration.ofMinutes(30);
  private final RedisCacheHelper redisCacheHelper;
  private final CategoryServiceImpl categoryServiceImpl;

  public List<CategoryDTO> cacheAllCategory() {
    return redisCacheHelper.getOrLoadList(
        CacheKeys.categories(), CACHE_TTL, categoryServiceImpl::getAllCategory, "categories");
  }
}

