package com.example.Makeup.service;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.dto.response.common.ApiResponse;

import java.util.List;

public interface IFeedBackService {

    ApiResponse<List<FeedBackDTO>> getGoodFeedBack(int minRating);
    ApiResponse<List<FeedBackDTO>> getAllFeedBack();
}
