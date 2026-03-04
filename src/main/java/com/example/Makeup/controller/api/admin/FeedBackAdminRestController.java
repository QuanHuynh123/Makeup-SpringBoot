package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IFeedBackService;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "FeedBack Admin API", description = "API for admin feedback operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/feedbacks")
public class FeedBackAdminRestController {

  private final IFeedBackService feedBackService;

  @Operation(summary = "Get all feedbacks", description = "Retrieve a list of all feedbacks")
  @GetMapping("")
  public ApiResponse<List<FeedBackDTO>> getAllFeedBack() {
    return ApiResponse.success("Get all feedbacks success",feedBackService.getAllFeedBack());
  }

  @Operation(summary = "Delete feedback", description = "Delete a feedback by feedback ID")
  @DeleteMapping("/{id}")
  public ApiResponse<String> deleteFeedBack(UUID id) {
    return ApiResponse.success(
            "Delete feedback success",feedBackService.deleteFeedBack(id));
  }

  @Operation(summary = "Get feedback by ID", description = "Retrieve feedback details by feedback ID")
  @GetMapping("/{id}")
  public ApiResponse<FeedBackDTO> getFeedBackById(UUID id) {
    return ApiResponse.success("Get feedback by ID success",feedBackService.getFeedBackById(id));
  }
}
