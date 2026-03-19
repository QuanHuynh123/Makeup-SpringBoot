package com.example.Makeup.service.common;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.service.impl.FeedBackServiceImpl;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheFeedBackService {

  private static final Duration CACHE_TTL = Duration.ofMinutes(30);
  private final RedisCacheHelper redisCacheHelper;
  private final FeedBackServiceImpl feedBackServiceImpl;

  public List<FeedBackDTO> cacheGoodFeedback() {
    return redisCacheHelper.getOrLoadList(
        CacheKeys.goodFeedbacks(),
        CACHE_TTL,
        () -> feedBackServiceImpl.getGoodFeedBack(4),
        "good feedbacks");
  }
}

