package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.FeedBackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheFeedBackService {

    public static final String GOOD_FEEDBACK_CACHE_KEY = "feedbacks-good";
    private final RedisTemplate<String, Object> redisTemplate;
    private final FeedBackService feedBackService;

    public ApiResponse<List<FeedBackDTO>> cacheGoodFeedback(){
        try {
            Object cached = redisTemplate.opsForValue().get(GOOD_FEEDBACK_CACHE_KEY);
            if (cached instanceof List<?> list && !list.isEmpty()) {
                return ApiResponse.<List<FeedBackDTO>>builder()
                        .code(200)
                        .message("Category list (from cache)")
                        .result((List<FeedBackDTO>) list)
                        .build();
            }
        } catch (Exception e) {
            System.out.println("⚠️ Redis GET failed, fallback to DB: " + e.getMessage());
        }
        return feedBackService.getGoodFeedBack(4);
    }
}
