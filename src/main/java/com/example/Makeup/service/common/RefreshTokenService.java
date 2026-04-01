package com.example.Makeup.service.common;

import com.example.Makeup.dto.response.AuthResponse;
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
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService implements IRefreshTokenService {

  private final JWTProvider jwtProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final RedisTemplate<String, Object> redisTemplate;
  public static final String REFRESH_TOKEN_PREFIX = "refresh-token:";

  @Value("${refresh.expiration}")
  private long refreshExpirationMs;

  @Transactional
  public AuthResponse refreshToken(String refreshToken) {

    log.info("Refreshing token for access token");

    RefreshTokenContext context = resolveRefreshContextRedisFirst(refreshToken);

    // Redis-first revocation path, DB sync comes right after.
    revokeRefreshTokenInRedisBestEffort(refreshToken, context.expiryDate());

    RefreshToken currentToken = refreshTokenRepository
            .findByToken(refreshToken)
            .orElseThrow(() -> new AppException(ErrorCode.AUTH_REFRESH_TOKEN_EXPIRED));
    if (currentToken.isRevoked() || currentToken.getExpiryDate().isBefore(LocalDateTime.now())) {
      throw new AppException(ErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
    }
    currentToken.setRevoked(true);
    refreshTokenRepository.save(currentToken);

    String username = currentToken.getAccount().getUserName();
    String role = currentToken.getAccount().getRole().getNameRole();
    UUID accountId = currentToken.getAccount().getId();

    String newRefreshToken = UUID.randomUUID().toString();
    LocalDateTime newExpiryDate = LocalDateTime.now().plus(Duration.ofMillis(refreshExpirationMs));

    RefreshToken replacementToken = new RefreshToken();
    replacementToken.setToken(newRefreshToken);
    replacementToken.setExpiryDate(newExpiryDate);
    replacementToken.setRevoked(false);
    replacementToken.setAccount(currentToken.getAccount());
    refreshTokenRepository.save(replacementToken);

    saveRefreshTokenBestEffort(newRefreshToken, accountId, username, role, newExpiryDate);

    String accessToken = jwtProvider.generateToken(
            username,
            role,
            accountId
    );

    return new AuthResponse(accessToken, newRefreshToken);
  }

  public void revokeRefreshToken(String refreshToken) {
    LocalDateTime expiryDate = resolveExpiryDateRedisFirst(refreshToken);
    revokeRefreshTokenInRedisBestEffort(refreshToken, expiryDate);

    RefreshToken token = refreshTokenRepository.findByToken(refreshToken).orElse(null);
    if (token != null) {
      token.setRevoked(true);
      refreshTokenRepository.save(token);
    }
  }

  public void revokeRefreshToken(String refreshToken, LocalDateTime expiryDate) {
    revokeRefreshTokenInRedisBestEffort(refreshToken, expiryDate);

    RefreshToken token = refreshTokenRepository.findByToken(refreshToken).orElse(null);
    if (token != null) {
      token.setRevoked(true);
      refreshTokenRepository.save(token);
    }
  }

  private void revokeRefreshTokenInRedisBestEffort(String refreshToken, LocalDateTime expiryDate) {
    String redisKey = REFRESH_TOKEN_PREFIX + refreshToken;
    Duration blacklistTtl = Duration.between(LocalDateTime.now(), expiryDate);
    if (blacklistTtl.isNegative() || blacklistTtl.isZero()) {
      blacklistTtl = Duration.ofMinutes(1);
    }

    try {
      redisTemplate.opsForValue().set(
              "blacklist:" + refreshToken,
              "revoked",
              blacklistTtl
      );
      redisTemplate.delete(redisKey);
      log.info("Revoked refresh token in Redis: {}", redisKey);
    } catch (Exception e) {
      log.warn("Redis unavailable while revoking refresh token {}: {}", redisKey, e.getMessage());
    }
  }

  private RefreshTokenContext resolveRefreshContextRedisFirst(String refreshToken) {
    try {
      String redisKey = REFRESH_TOKEN_PREFIX + refreshToken;
      Boolean isBlacklisted = redisTemplate.hasKey("blacklist:" + refreshToken);
      if (Boolean.TRUE.equals(isBlacklisted)) {
        throw new AppException(ErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
      }

      Map<Object, Object> refreshData = redisTemplate.opsForHash().entries(redisKey);
      if (!refreshData.isEmpty()) {
        String accountId = (String) refreshData.get("accountId");
        String username = (String) refreshData.get("username");
        String role = (String) refreshData.get("roles");
        String expiry = (String) refreshData.get("expiryDate");

        if (accountId != null && username != null && role != null && expiry != null) {
          LocalDateTime expiryDate = LocalDateTime.parse(expiry);
          if (expiryDate.isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
          }
          return new RefreshTokenContext(UUID.fromString(accountId), username, role, expiryDate);
        }
      }

      log.info("Refresh token not found in Redis. Fallback to DB.");
      return resolveRefreshContextFromDb(refreshToken);
    } catch (AppException e) {
      throw e;
    } catch (Exception e) {
      log.warn("Redis unavailable while resolving refresh token. Fallback DB: {}", e.getMessage());
      return resolveRefreshContextFromDb(refreshToken);
    }
  }

  private RefreshTokenContext resolveRefreshContextFromDb(String refreshToken) {
    RefreshToken token = refreshTokenRepository
            .findByToken(refreshToken)
            .orElseThrow(() -> new AppException(ErrorCode.AUTH_REFRESH_TOKEN_EXPIRED));

    if (token.isRevoked() || token.getExpiryDate().isBefore(LocalDateTime.now())) {
      throw new AppException(ErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
    }

    saveRefreshTokenBestEffort(
            token.getToken(),
            token.getAccount().getId(),
            token.getAccount().getUserName(),
            token.getAccount().getRole().getNameRole(),
            token.getExpiryDate()
    );

    return new RefreshTokenContext(
            token.getAccount().getId(),
            token.getAccount().getUserName(),
            token.getAccount().getRole().getNameRole(),
            token.getExpiryDate()
    );
  }

  private LocalDateTime resolveExpiryDateRedisFirst(String refreshToken) {
    try {
      String redisKey = REFRESH_TOKEN_PREFIX + refreshToken;
      Map<Object, Object> refreshData = redisTemplate.opsForHash().entries(redisKey);
      String expiry = (String) refreshData.get("expiryDate");
      if (expiry != null) {
        return LocalDateTime.parse(expiry);
      }
    } catch (Exception e) {
      log.warn("Redis unavailable while reading refresh expiry. Fallback DB: {}", e.getMessage());
    }

    RefreshToken token = refreshTokenRepository.findByToken(refreshToken).orElse(null);
    if (token != null) {
      return token.getExpiryDate();
    }
    return LocalDateTime.now().plus(Duration.ofMillis(refreshExpirationMs));
  }

  private void saveRefreshTokenBestEffort(String refreshToken, UUID accountId,
                                          String username, String role, LocalDateTime expiryDate) {
    try {
      saveRefreshToken(refreshToken, accountId, username, role, expiryDate);
    } catch (Exception e) {
      log.warn("Redis unavailable while caching refresh token {}: {}", refreshToken, e.getMessage());
    }
  }

  public void saveRefreshToken(String refreshToken, UUID accountId,
                               String username, String role, LocalDateTime expiryDate) {

    String redisKey = REFRESH_TOKEN_PREFIX + refreshToken;

    Map<String, Object> refreshData = new HashMap<>();
    refreshData.put("accountId", accountId.toString());
    refreshData.put("username", username);
    refreshData.put("roles", role);
    refreshData.put("expiryDate", expiryDate.toString());

    redisTemplate.opsForHash().putAll(redisKey, refreshData);
    Duration ttl = Duration.between(LocalDateTime.now(), expiryDate);
    if (!ttl.isNegative() && !ttl.isZero()) {
      redisTemplate.expire(redisKey, ttl);
    }

    log.info("Saved refresh token in Redis with key={}", redisKey);
  }

  private record RefreshTokenContext(
          UUID accountId,
          String username,
          String role,
          LocalDateTime expiryDate
  ) {}
}
