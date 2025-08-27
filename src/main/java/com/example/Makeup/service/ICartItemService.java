package com.example.Makeup.service;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.dto.model.CartItemDTO;
import com.example.Makeup.dto.request.UpdateCartItemRequest;
import com.example.Makeup.dto.response.CartItemResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.util.List;
import java.util.UUID;

public interface ICartItemService {

  ApiResponse<List<CartItemResponse>> getCartItemByCartId();

  ApiResponse<Boolean> addCartItem(CartItemDTO cartItemDTO);

  ApiResponse<Boolean> deleteCartItem(UUID cartItemId);

  ApiResponse<CartDTO> updateCartItem(UpdateCartItemRequest cartItemRequest);

  ApiResponse<Boolean> deleteAllCartItem();
}
