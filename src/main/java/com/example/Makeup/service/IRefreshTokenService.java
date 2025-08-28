package com.example.Makeup.service;

import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.RefreshToken;
import java.util.UUID;

public interface IRefreshTokenService {

  String refreshToken(String refreshToken);

//  RefreshToken getTokenByAccountId(UUID accountId);
}
