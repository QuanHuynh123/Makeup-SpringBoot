package com.example.Makeup.service;

import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.RefreshToken;

import java.util.UUID;

public interface IRefreshTokenService {

    ApiResponse<String> refreshToken(String accessToken, UUID accountId);
    RefreshToken getTokenByAccountId(UUID accountId);
}
