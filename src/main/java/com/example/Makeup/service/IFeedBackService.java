package com.example.Makeup.service;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.dto.request.CreateFeedBackRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.util.List;
import java.util.UUID;

public interface IFeedBackService {

  ApiResponse<List<FeedBackDTO>> getGoodFeedBack(int minRating);

  ApiResponse<List<FeedBackDTO>> getAllFeedBack();

  ApiResponse<String> deleteFeedBack(UUID id);

  ApiResponse<FeedBackDTO> getFeedBackById(UUID id);

  ApiResponse<FeedBackDTO> createFeedBack(CreateFeedBackRequest createFeedBackRequest);
}
