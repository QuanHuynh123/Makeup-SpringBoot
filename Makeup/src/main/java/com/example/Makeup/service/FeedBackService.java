package com.example.Makeup.service;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.entity.FeedBack;
import com.example.Makeup.enums.ApiResponse;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.FeedbackMapper;
import com.example.Makeup.repository.FeedBackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackService {

    private  final FeedBackRepository feedBackRepository;
    private final FeedbackMapper feedbackMapper;

    public FeedBackService(FeedBackRepository feedBackRepository, FeedbackMapper feedbackMapper) {
        this.feedBackRepository = feedBackRepository;
        this.feedbackMapper = feedbackMapper;
    }

    public ApiResponse<List<FeedBackDTO>> getAllFeedBack (){
        List<FeedBack> feedBacks = feedBackRepository.findAll();
        if (feedBacks.isEmpty()) throw new AppException(ErrorCode.IS_EMPTY);
        return ApiResponse.<List<FeedBackDTO>>builder()
                .code(200)
                .message("Feedbacks found")
                .result(feedBacks.stream()
                        .map(feedbackMapper::toFeedBackDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public ApiResponse<List<FeedBackDTO>> getGoodFeedback(int minRating){
        List<FeedBack> feedBacks =  feedBackRepository.findByRatingGreaterThanEqual(minRating);
        if (feedBacks.isEmpty()) {
            return ApiResponse.<List<FeedBackDTO>>builder().code(404)
                    .message("No feedback found with rating >= " + minRating).build();
        }

        return ApiResponse.<List<FeedBackDTO>>builder()
                .code(200).message("Feedbacks found").result(feedBacks.stream()
                        .map(feedbackMapper::toFeedBackDTO).collect(Collectors.toList())).build();
    }

}
    