package com.example.Makeup.service.common;

import com.example.Makeup.dto.response.ShortProductListResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheProductService {

    private static final String HOT_PRODUCTS_CACHE_KEY = "products-hot";
    private static final String NEW_PRODUCTS_CACHE_KEY = "products-new";
    private static final String CUSTOMER_SHOW_CACHE_KEY = "products-customer-show";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductService productService;

    public ApiResponse<List<ShortProductListResponse>> cacheHotProducts() {
        try {
            Object cached = redisTemplate.opsForValue().get(HOT_PRODUCTS_CACHE_KEY);
            if (cached instanceof List<?> list && !list.isEmpty()) {
                return ApiResponse.success( "Hot products (from cache)",(List<ShortProductListResponse>) list);
            }
        } catch (Exception e) {
            log.warn("⚠️ Redis GET failed (hot products), fallback to DB: " + e.getMessage());
        }

        return productService.getHotProducts();
    }

    public ApiResponse<List<ShortProductListResponse>> cacheNewProducts() {
        try {
            Object cached = redisTemplate.opsForValue().get(NEW_PRODUCTS_CACHE_KEY);
            if (cached instanceof List<?> list && !list.isEmpty()) {
                return ApiResponse.success("New products (from cache)",(List<ShortProductListResponse>) list );
            }
        } catch (Exception e) {
            log.warn("⚠️ Redis GET failed (new products), fallback to DB: " + e.getMessage());
        }

        return productService.getNewProducts();
    }

    public ApiResponse<List<ShortProductListResponse>> cacheCustomerShow() {
        try {
            Object cached = redisTemplate.opsForValue().get(CUSTOMER_SHOW_CACHE_KEY);
            if (cached instanceof List<?> list && !list.isEmpty()) {
                return ApiResponse.success("Customer show products (from cache)",(List<ShortProductListResponse>) list );
            }
        } catch (Exception e) {
            log.warn("⚠️ Redis GET failed (new products), fallback to DB: " + e.getMessage());
        }

        return productService.getCustomerShowProducts();
    }
}
