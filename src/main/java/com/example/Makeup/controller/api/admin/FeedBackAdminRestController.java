package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IFeedBackService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/feedbacks")
public class FeedBackAdminRestController {

  private final IFeedBackService feedBackService;

  @GetMapping("")
  public ApiResponse<List<FeedBackDTO>> getAllFeedBack() {
    return ApiResponse.success("Get all feedbacks success",feedBackService.getAllFeedBack());
  }

  @DeleteMapping("/{id}/delete")
  public ApiResponse<String> deleteFeedBack(UUID id) {
    return ApiResponse.success(
            "Delete feedback success",feedBackService.deleteFeedBack(id));
  }

  @GetMapping("/{id}")
  public ApiResponse<FeedBackDTO> getFeedBackById(UUID id) {
    return ApiResponse.success("Get feedback by ID success",feedBackService.getFeedBackById(id));
  }
}
