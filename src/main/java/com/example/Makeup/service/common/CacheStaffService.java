package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.service.impl.StaffServiceImpl;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheStaffService {

  private static final Duration CACHE_TTL = Duration.ofMinutes(30);
  private final RedisCacheHelper redisCacheHelper;
  private final StaffServiceImpl staffServiceImpl;

  public List<StaffDTO> cacheAllStaff() {
    return redisCacheHelper.getOrLoadList(
        CacheKeys.staffs(), CACHE_TTL, staffServiceImpl::getAllStaff, "staff list");
  }
}

