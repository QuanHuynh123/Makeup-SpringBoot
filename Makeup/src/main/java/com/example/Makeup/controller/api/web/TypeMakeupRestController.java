package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.ITypeMakeupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/typeMakeups")
public class TypeMakeupRestController {

    private final ITypeMakeupService typeMakeupService;

    @GetMapping
    public ApiResponse<?> getAllTypeMakeups() {
        return  typeMakeupService.getAllTypeMakeup();
    }
}
