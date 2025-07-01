package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IStaffService;
import com.example.Makeup.service.impl.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheStaffService {

    private static final String STAFF_CACHE_KEY = "staffs";
    private final RedisTemplate<String, Object> redisTemplate;
    private final StaffService staffService;

    public ApiResponse<List<StaffDTO>> cacheAllStaff() {
        try {
            Object cached = redisTemplate.opsForValue().get(STAFF_CACHE_KEY);
            if (cached instanceof List<?> list && !list.isEmpty()) {
                return ApiResponse.success("Staff list (from cache)",(List<StaffDTO>) list);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Redis GET failed: " + e.getMessage());
        }

        return staffService.getAllStaff();
    }
}
