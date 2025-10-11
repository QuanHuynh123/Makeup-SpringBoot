package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.service.impl.FeedBackServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheFeedBackService {

  private static final String GOOD_FEEDBACK_CACHE_KEY = "feedbacks-good";
  private final RedisTemplate<String, Object> redisTemplate;
  private final FeedBackServiceImpl feedBackServiceImpl;

  public List<FeedBackDTO> cacheGoodFeedback() {
    try {
      Object cached = redisTemplate.opsForValue().get(GOOD_FEEDBACK_CACHE_KEY);
      if (cached instanceof List<?> list && !list.isEmpty()) {
        log.debug("Feedbacks loaded from Redis cache");
        return (List<FeedBackDTO>) list;
      }
    } catch (Exception e) {
      log.warn("⚠️ Redis GET failed (feedbacks), fallback to DB: {}", e.getMessage());
    }

    List<FeedBackDTO> fromDb = feedBackServiceImpl.getGoodFeedBack(4);
    try {
      redisTemplate.opsForValue().set(GOOD_FEEDBACK_CACHE_KEY, fromDb);
      log.debug("Feedbacks cached to Redis");
    } catch (Exception e) {
      log.warn("⚠️ Failed to cache feedbacks: {}", e.getMessage());
    }

    return fromDb;
  }
}

