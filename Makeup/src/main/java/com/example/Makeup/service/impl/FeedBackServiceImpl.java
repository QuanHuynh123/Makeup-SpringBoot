package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.request.CreateFeedBackRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.FeedBack;
import com.example.Makeup.entity.User;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.mapper.FeedbackMapper;
import com.example.Makeup.repository.FeedBackRepository;
import com.example.Makeup.repository.UserRepository;
import com.example.Makeup.service.IFeedBackService;
import com.example.Makeup.utils.SecurityUserUtil;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedBackServiceImpl implements IFeedBackService {

  public static final String GOOD_FEEDBACK_CACHE_KEY = "feedbacks-good";

  private final FeedBackRepository feedBackRepository;
  private final FeedbackMapper feedbackMapper;
  private final RedisTemplate<String, Object> redisTemplate;
  private final UserRepository userRepository;

  @Override
  public ApiResponse<List<FeedBackDTO>> getGoodFeedBack(int minRating) {
    List<FeedBack> feedBacks = feedBackRepository.findByRatingGreaterThanEqual(minRating);
    List<FeedBackDTO> dtos =
        feedBacks.stream().map(feedbackMapper::toFeedBackDTO).collect(Collectors.toList());

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
  public ApiResponse<List<FeedBackDTO>> getAllFeedBack() {
    List<FeedBack> feedBacks = feedBackRepository.findAll();
    if (feedBacks.isEmpty()) throw new AppException(ErrorCode.COMMON_IS_EMPTY);
    List<FeedBackDTO> feedBackDTOS =
        feedBacks.stream().map(feedbackMapper::toFeedBackDTO).collect(Collectors.toList());
    return ApiResponse.success("Get all feedbacks success", feedBackDTOS);
  }

  @Override
  public ApiResponse<String> deleteFeedBack(UUID id) {
    FeedBack feedBack =
        feedBackRepository
            .findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND));
    feedBackRepository.delete(feedBack);
    return ApiResponse.success(
        "Delete feedback success", "Feedback with ID " + id + " has been deleted");
  }

  @Override
  public ApiResponse<FeedBackDTO> getFeedBackById(UUID id) {
    FeedBack feedBack =
        feedBackRepository
            .findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND));
    FeedBackDTO feedBackDTO = feedbackMapper.toFeedBackDTO(feedBack);
    return ApiResponse.success("Get feedback by ID success", feedBackDTO);
  }

  @Override
  public ApiResponse<FeedBackDTO> createFeedBack(CreateFeedBackRequest createFeedBackRequest) {
    UserDTO currentUser = SecurityUserUtil.getCurrentUser();
    User user =
        userRepository
            .findById(currentUser.getId())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

    FeedBack newFeedBack = new FeedBack();
    newFeedBack.setComment(createFeedBackRequest.getComment());
    newFeedBack.setRating(createFeedBackRequest.getRating());
    newFeedBack.setUser(user);

    return ApiResponse.success(
        "Create feedback success",
        feedbackMapper.toFeedBackDTO(feedBackRepository.save(newFeedBack)));
  }
}
