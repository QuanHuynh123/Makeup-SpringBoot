package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.ITypeMakeupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Type Makeup API", description = "API for type makeup operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/typeMakeups")
public class TypeMakeupRestController {

  private final ITypeMakeupService typeMakeupService;

  @Operation(summary = "Get all type makeups", description = "Retrieve a list of all type makeups")
  @GetMapping
  public ApiResponse<?> getAllTypeMakeups() {
    return ApiResponse.success("Get all services success (from DB)",typeMakeupService.getAllTypeMakeup());
  }
}
