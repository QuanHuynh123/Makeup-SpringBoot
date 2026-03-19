package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.TypeMakeupDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.TypeMakeupServiceImpl;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheTypeMakeupService {

  private static final Duration CACHE_TTL = Duration.ofMinutes(30);
  private final RedisCacheHelper redisCacheHelper;
  private final TypeMakeupServiceImpl typeMakeupServiceImpl;

  public ApiResponse<List<TypeMakeupDTO>> cacheAllTypeMakeup() {
    List<TypeMakeupDTO> fromDb =
        redisCacheHelper.getOrLoadList(
            CacheKeys.typeMakeups(),
            CACHE_TTL,
            typeMakeupServiceImpl::getAllTypeMakeup,
            "type makeups");
    return ApiResponse.success("Get all services success (from DB)", fromDb);
  }
}
