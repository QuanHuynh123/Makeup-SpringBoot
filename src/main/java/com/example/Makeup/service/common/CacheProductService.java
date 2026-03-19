package com.example.Makeup.service.common;

import com.example.Makeup.dto.response.ShortProductListResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.ProductServiceImpl;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheProductService {

  private static final Duration CACHE_TTL = Duration.ofMinutes(30);

  private final RedisCacheHelper redisCacheHelper;
  private final ProductServiceImpl productServiceImpl;

  public ApiResponse<List<ShortProductListResponse>> cacheHotProducts() {
    List<ShortProductListResponse> result =
        redisCacheHelper.getOrLoadList(
            CacheKeys.hotProducts(),
            CACHE_TTL,
            productServiceImpl::getHotProducts,
            "hot products");
    return ApiResponse.success("Hot products (from DB)", result);
  }

  public ApiResponse<List<ShortProductListResponse>> cacheNewProducts() {
    List<ShortProductListResponse> result =
        redisCacheHelper.getOrLoadList(
            CacheKeys.newProducts(),
            CACHE_TTL,
            productServiceImpl::getNewProducts,
            "new products");
    return ApiResponse.success("New products (from DB)", result);
  }

  public List<ShortProductListResponse> cacheCustomerShow() {
    return redisCacheHelper.getOrLoadList(
        CacheKeys.customerShowProducts(),
        CACHE_TTL,
        productServiceImpl::getCustomerShowProducts,
        "customer show products");
  }
}

