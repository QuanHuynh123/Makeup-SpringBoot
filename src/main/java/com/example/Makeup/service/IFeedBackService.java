package com.example.Makeup.service;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.dto.request.CreateFeedBackRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.util.List;
import java.util.UUID;

public interface IFeedBackService {

  List<FeedBackDTO> getGoodFeedBack(int minRating);

  List<FeedBackDTO> getAllFeedBack();

  String deleteFeedBack(UUID id);

  FeedBackDTO getFeedBackById(UUID id);

  FeedBackDTO createFeedBack(CreateFeedBackRequest createFeedBackRequest);
}
