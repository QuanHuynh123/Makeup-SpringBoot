package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.dto.model.CartItemDTO;
import com.example.Makeup.dto.request.UpdateCartItemRequest;
import com.example.Makeup.dto.response.CartItemResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.ICartItemService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CartRestController {

  private final ICartItemService cartItemService;

  @GetMapping()
  public ApiResponse<List<CartItemResponse>> getCartItemByCartId() {
    return ApiResponse.success("Get cart items by cart ID success", cartItemService.getCartItemByCartId());
  }

  @PostMapping("/add")
  public ApiResponse<Boolean> addItemToCart(@RequestBody CartItemDTO cartRequest) {
    return ApiResponse.success("Add cart item success",cartItemService.addCartItem(cartRequest));
  }

  @PostMapping("/update")
  public ApiResponse<CartDTO> updateToCart(
      @RequestBody UpdateCartItemRequest updateCartItemRequest) {
    return ApiResponse.success("Cart update success !",cartItemService.updateCartItem(updateCartItemRequest));
  }

  @DeleteMapping("/delete")
  public ApiResponse<Boolean> deleteToCart(@RequestParam UUID cartItemId) {
    return ApiResponse.success("Delete cart item success",cartItemService.deleteCartItem(cartItemId));
  }

  @DeleteMapping("/delete-all")
  public ApiResponse<Boolean> deleteAllToCart() {
    return ApiResponse.success("Delete all cart items success",cartItemService.deleteAllCartItem());
  }
}
