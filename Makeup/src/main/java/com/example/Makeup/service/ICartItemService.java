package com.example.Makeup.service;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.dto.model.CartItemDTO;
import com.example.Makeup.dto.response.common.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface ICartItemService {

    ApiResponse<List<CartItemDTO>> getCartItemByCartId(UUID cartId);
    ApiResponse<Boolean> addCartItem(CartItemDTO cartItemDTO, UUID cartId);
    ApiResponse<Boolean> deleteCartItem(UUID cartItemId, UUID cartId);
    ApiResponse<CartDTO> updateCartItem(CartItemDTO cartItemDTO, UUID cartId);
}
