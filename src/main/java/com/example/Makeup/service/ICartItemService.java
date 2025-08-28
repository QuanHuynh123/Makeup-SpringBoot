package com.example.Makeup.service;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.dto.model.CartItemDTO;
import com.example.Makeup.dto.request.UpdateCartItemRequest;
import com.example.Makeup.dto.response.CartItemResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.util.List;
import java.util.UUID;

public interface ICartItemService {

  List<CartItemResponse> getCartItemByCartId();

  Boolean addCartItem(CartItemDTO cartItemDTO);

  Boolean deleteCartItem(UUID cartItemId);

  CartDTO updateCartItem(UpdateCartItemRequest cartItemRequest);

  Boolean deleteAllCartItem();
}
