package com.example.Makeup.service.common;

import com.example.Makeup.dto.response.ShortProductListResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.ProductServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheProductService {

  private static final String HOT_PRODUCTS_CACHE_KEY = "products-hot";
  private static final String NEW_PRODUCTS_CACHE_KEY = "products-new";
  private static final String CUSTOMER_SHOW_CACHE_KEY = "products-customer-show";

  private final RedisTemplate<String, Object> redisTemplate;
  private final ProductServiceImpl productServiceImpl;

  public ApiResponse<List<ShortProductListResponse>> cacheHotProducts() {
    List<ShortProductListResponse> result = getListFromCache(HOT_PRODUCTS_CACHE_KEY);
    if (result != null && !result.isEmpty()) {
      return ApiResponse.success("Hot products (from cache)", result);
    }

    result = productServiceImpl.getHotProducts();
    cacheData(HOT_PRODUCTS_CACHE_KEY, result);
    return ApiResponse.success("Hot products (from DB)", result);
  }

  public ApiResponse<List<ShortProductListResponse>> cacheNewProducts() {
    List<ShortProductListResponse> result = getListFromCache(NEW_PRODUCTS_CACHE_KEY);
    if (result != null && !result.isEmpty()) {
      return ApiResponse.success("New products (from cache)", result);
    }

    result = productServiceImpl.getNewProducts();
    cacheData(NEW_PRODUCTS_CACHE_KEY, result);
    return ApiResponse.success("New products (from DB)", result);
  }

  public List<ShortProductListResponse> cacheCustomerShow() {
    List<ShortProductListResponse> result = getListFromCache(CUSTOMER_SHOW_CACHE_KEY);
    if (result != null && !result.isEmpty()) {
      return result;
    }

    result = productServiceImpl.getCustomerShowProducts();
    cacheData(CUSTOMER_SHOW_CACHE_KEY, result);
    return result;
  }

  // --- Helpers ---

  @SuppressWarnings("unchecked")
  private List<ShortProductListResponse> getListFromCache(String key) {
    try {
      Object cached = redisTemplate.opsForValue().get(key);
      if (cached instanceof List<?> list) {
        log.debug("✅ Cache hit for key: {}", key);
        return (List<ShortProductListResponse>) list;
      }
    } catch (Exception e) {
      log.warn("⚠️ Redis GET failed for key {}: {}", key, e.getMessage());
    }
    return null;
  }

  private void cacheData(String key, List<?> data) {
    try {
      redisTemplate.opsForValue().set(key, data);
      log.debug("💾 Cached data for key: {}", key);
    } catch (Exception e) {
      log.warn("⚠️ Failed to cache data for key {}: {}", key, e.getMessage());
    }
  }
}

