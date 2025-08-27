package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.impl.FeedBackServiceImpl;
import com.example.Makeup.utils.RedisStatusManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheFeedBackService {

  public static final String GOOD_FEEDBACK_CACHE_KEY = "feedbacks-good";
  private final RedisTemplate<String, Object> redisTemplate;
  private final FeedBackServiceImpl feedBackServiceImpl;

  public ApiResponse<List<FeedBackDTO>> cacheGoodFeedback() {

    if (!RedisStatusManager.isRedisAvailable()) {
      return feedBackServiceImpl.getGoodFeedBack(4);
    }

    try {
      Object cached = redisTemplate.opsForValue().get(GOOD_FEEDBACK_CACHE_KEY);
      if (cached instanceof List<?> list && !list.isEmpty()) {
        return ApiResponse.success("Category list (from cache)", (List<FeedBackDTO>) list);
      }
    } catch (RedisConnectionFailureException e) {
      log.warn("⚠️ Redis connection failed: {}", e.getMessage());
      RedisStatusManager.setRedisAvailable(false);
    } catch (Exception e) {
      log.warn("⚠️ Redis GET failed, fallback to DB: {}", e.getMessage());
    }
    return feedBackServiceImpl.getGoodFeedBack(4);
  }
}
