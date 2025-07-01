package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.entity.FeedBack;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.FeedbackMapper;
import com.example.Makeup.repository.FeedBackRepository;
import com.example.Makeup.service.IFeedBackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedBackService implements IFeedBackService {

    public static final String GOOD_FEEDBACK_CACHE_KEY = "feedbacks-good";

    private  final FeedBackRepository feedBackRepository;
    private final FeedbackMapper feedbackMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public ApiResponse<List<FeedBackDTO>> getGoodFeedBack(int minRating) {
        List<FeedBack> feedBacks =  feedBackRepository.findByRatingGreaterThanEqual(minRating);
        List<FeedBackDTO> dtos = feedBacks.stream()
                .map(feedbackMapper::toFeedBackDTO)
                .collect(Collectors.toList());

        try {
            redisTemplate.opsForValue().set(GOOD_FEEDBACK_CACHE_KEY, dtos, Duration.ofMinutes(30));
        } catch (Exception e) {
            System.out.println("⚠️ Redis SET failed: " + e.getMessage());
        }

        return ApiResponse.<List<FeedBackDTO>>builder()
                .code(200)
                .message(dtos.isEmpty() ? "No feedback found" : "Feedbacks found")
                .result(dtos)
                .build();
    }

    @Override
    public ApiResponse<List<FeedBackDTO>> getAllFeedBack (){
        List<FeedBack> feedBacks = feedBackRepository.findAll();
        if (feedBacks.isEmpty()) throw new AppException(ErrorCode.COMMON_IS_EMPTY);
        List<FeedBackDTO> feedBackDTOS = feedBacks.stream()
                .map(feedbackMapper::toFeedBackDTO)
                .collect(Collectors.toList());
        return ApiResponse.success("Get all feedbacks success", feedBackDTOS);
    }

}
    