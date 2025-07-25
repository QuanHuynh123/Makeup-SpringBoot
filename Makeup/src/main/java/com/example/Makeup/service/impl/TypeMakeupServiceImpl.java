package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.TypeMakeupDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.TypeMakeup;
import com.example.Makeup.mapper.TypeMakeupMapper;
import com.example.Makeup.repository.TypeMakeupRepository;
import com.example.Makeup.service.ITypeMakeupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeMakeupServiceImpl implements ITypeMakeupService {

    private static final String SERVICE_MAKEUP_CACHE_KEY = "type-makeup";

    private final TypeMakeupRepository typeMakeupRepository;
    private final TypeMakeupMapper typeMakeupMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public ApiResponse<List<TypeMakeupDTO>> getAllTypeMakeup() {
        List<TypeMakeup> typeMakeups = typeMakeupRepository.findAll();
        List<TypeMakeupDTO> dtos = typeMakeups.stream()
                .map(typeMakeupMapper::toTypeMakeupDTO)
                .collect(Collectors.toList());

        try {
            redisTemplate.opsForValue().set(SERVICE_MAKEUP_CACHE_KEY, dtos, Duration.ofMinutes(30));
        } catch (Exception e) {
            System.out.println("⚠️ Redis SET failed: " + e.getMessage());
        }

        return ApiResponse.success("Get all services success (from DB)",dtos);
    }
}
