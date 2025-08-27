package com.example.Makeup.service.common;

import com.example.Makeup.dto.response.ShortProductListResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.ProductServiceImpl;
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
public class CacheProductService {

  private static final String HOT_PRODUCTS_CACHE_KEY = "products-hot";
  private static final String NEW_PRODUCTS_CACHE_KEY = "products-new";
  private static final String CUSTOMER_SHOW_CACHE_KEY = "products-customer-show";

  private final RedisTemplate<String, Object> redisTemplate;
  private final ProductServiceImpl productServiceImpl;

  public ApiResponse<List<ShortProductListResponse>> cacheHotProducts() {
    if (!RedisStatusManager.isRedisAvailable()) {
      return productServiceImpl.getHotProducts();
    }

    try {
      Object cached = redisTemplate.opsForValue().get(HOT_PRODUCTS_CACHE_KEY);
      if (cached instanceof List<?> list && !list.isEmpty()) {
        return ApiResponse.success(
            "Hot products (from cache)", (List<ShortProductListResponse>) list);
      }
    } catch (RedisConnectionFailureException e) {
      log.warn("⚠️ Redis connection failed (hot products): {}", e.getMessage());
      RedisStatusManager.setRedisAvailable(false);
    } catch (Exception e) {
      log.warn("⚠️ Redis GET failed (hot products), fallback to DB: {}", e.getMessage());
    }

    return productServiceImpl.getHotProducts();
  }

  public ApiResponse<List<ShortProductListResponse>> cacheNewProducts() {
    if (!RedisStatusManager.isRedisAvailable()) {
      return productServiceImpl.getNewProducts();
    }

    try {
      Object cached = redisTemplate.opsForValue().get(NEW_PRODUCTS_CACHE_KEY);
      if (cached instanceof List<?> list && !list.isEmpty()) {
        return ApiResponse.success(
            "New products (from cache)", (List<ShortProductListResponse>) list);
      }
    } catch (RedisConnectionFailureException e) {
      log.warn("⚠️ Redis connection failed (new products): {}", e.getMessage());
      RedisStatusManager.setRedisAvailable(false);
    } catch (Exception e) {
      log.warn("⚠️ Redis GET failed (new products), fallback to DB: {}", e.getMessage());
    }

    return productServiceImpl.getNewProducts();
  }

  public ApiResponse<List<ShortProductListResponse>> cacheCustomerShow() {
    if (!RedisStatusManager.isRedisAvailable()) {
      return productServiceImpl.getCustomerShowProducts();
    }

    try {
      Object cached = redisTemplate.opsForValue().get(CUSTOMER_SHOW_CACHE_KEY);
      if (cached instanceof List<?> list && !list.isEmpty()) {
        return ApiResponse.success(
            "Customer show products (from cache)", (List<ShortProductListResponse>) list);
      }
    } catch (RedisConnectionFailureException e) {
      log.warn("⚠️ Redis connection failed (customer show products): {}", e.getMessage());
      RedisStatusManager.setRedisAvailable(false);
    } catch (Exception e) {
      log.warn("⚠️ Redis GET failed (customer show products), fallback to DB: {}", e.getMessage());
    }

    return productServiceImpl.getCustomerShowProducts();
  }
}
