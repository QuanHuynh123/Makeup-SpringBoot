package com.example.Makeup.service.common;

import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.RefreshToken;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.repository.RefreshTokenRepository;
import com.example.Makeup.security.JWTProvider;
import com.example.Makeup.service.IRefreshTokenService;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService implements IRefreshTokenService {

  private final JWTProvider jwtProvider;
  private final RedisTemplate<String, Object> redisTemplate;

  @Transactional
  public String refreshToken(String refreshToken) {

    log.info("Refreshing token for access token");

    String redisKey = "refreshToken:" + refreshToken;

    Map<Object, Object> refreshData = redisTemplate.opsForHash().entries(redisKey);

    if (refreshData == null || refreshData.isEmpty()) {
      log.error("Refresh token not found or expired in Redis");
      throw new AppException(ErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
    }

    String accountId = (String) refreshData.get("accountId");
    String username = (String) refreshData.get("username");
    String role = (String) refreshData.get("roles");

    if (accountId == null || username == null || role == null) {
      log.error("Invalid refresh token data in Redis for key={}", redisKey);
      throw new AppException(ErrorCode.AUTH_REFRESH_TOKEN_NOT_FOUND);
    }

    return jwtProvider.generateToken(
            username,
            role,
            UUID.fromString(accountId)
    );
  }

  public void revokeRefreshToken(String refreshToken) {
    String redisKey = "refreshToken:" + refreshToken;
    redisTemplate.delete(redisKey);
    log.info("Revoked refresh token in Redis: {}", redisKey);
  }

  public void saveRefreshToken(String refreshToken, UUID accountId,
                               String username, String role, LocalDateTime expiryDate) {

    String redisKey = "refreshToken:" + refreshToken;

    Map<String, Object> refreshData = new HashMap<>();
    refreshData.put("accountId", accountId.toString());
    refreshData.put("username", username);
    refreshData.put("roles", role);
    refreshData.put("expiryDate", expiryDate.toString());

    redisTemplate.opsForHash().putAll(redisKey, refreshData);
    redisTemplate.expire(redisKey, Duration.between(LocalDateTime.now(), expiryDate));

    log.info("Saved refresh token in Redis with key={}", redisKey);
  }
}
