package com.example.Makeup.service;

import com.example.Makeup.dto.response.AuthResponse;

public interface IRefreshTokenService {

  AuthResponse refreshToken(String refreshToken);

//  RefreshToken getTokenByAccountId(UUID accountId);
}
