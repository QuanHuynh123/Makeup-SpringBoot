package com.example.Makeup.service;

import com.example.Makeup.dto.model.ServiceMakeupDTO;
import com.example.Makeup.entity.ServiceMakeup;
import com.example.Makeup.enums.ApiResponse;
import com.example.Makeup.mapper.ServiceMakeupMapper;
import com.example.Makeup.repository.ServiceMakeupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceMakeupService {

    private final ServiceMakeupRepository serviceMakeupRepository;
    private final ServiceMakeupMapper serviceMakeupMapper;

    public ApiResponse<List<ServiceMakeupDTO>> getAllServices() {
        List<ServiceMakeup> serviceMakeups = serviceMakeupRepository.findAll();
        return ApiResponse.<List<ServiceMakeupDTO>>builder()
                .code(200)
                .message("Get all services success")
                .result(serviceMakeups.stream()
                        .map(serviceMakeupMapper::toServiceMakeupDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
