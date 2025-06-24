package com.example.Makeup.service;


import com.example.Makeup.entity.RefreshToken;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.repository.RefreshTokenRepository;
import com.example.Makeup.security.JWTProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTProvider jwtProvider;

    @Transactional
    public ApiResponse<String> refreshToken(String accessToken, UUID accountId) {
        log.info("Refreshing token for access token: {}", accessToken);
        String username = jwtProvider.extractUserNameAllowExpired(accessToken);
        int role = jwtProvider.extractRoleAllowExpired(accessToken);

        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findLatestValidTokenByAccount(accountId);
        if (refreshTokenOpt.isPresent()) {
            RefreshToken refreshToken = refreshTokenOpt.get();
            if (!refreshToken.isRevoked() && refreshToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                String newAccessToken = jwtProvider.generateToken(username, role, accountId);
                return ApiResponse.<String>builder()
                        .code(200)
                        .message("Token refreshed successfully")
                        .result(newAccessToken)
                        .build();
            }
        }
        log.error("Refresh token not found or expired for account ID: {}", accountId);
        throw new AppException(ErrorCode.AUTH_REFRESH_TOKEN_NOT_FOUND);
    }

    public RefreshToken  getTokenByAccountId(UUID accountId) {
        log.info("Fetching latest valid refresh token for account ID: {}", accountId);
        return refreshTokenRepository.findLatestValidTokenByAccount(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.AUTH_REFRESH_TOKEN_NOT_FOUND));
    }
}
