package com.example.Makeup.service;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.util.UUID;

public interface ICartService {

  ApiResponse<CartDTO> getCart(UUID userId);

  ApiResponse<CartDTO> createCart(UUID accountId);
}
