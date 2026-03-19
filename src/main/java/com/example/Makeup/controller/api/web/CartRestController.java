package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.dto.model.CartItemDTO;
import com.example.Makeup.dto.request.UpdateCartItemRequest;
import com.example.Makeup.dto.response.CartItemResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.ICartItemService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart API", description = "API for cart operations")
@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CartRestController {

  private final ICartItemService cartItemService;

  @Operation(summary = "Get cart items by cart ID", description = "Retrieve a list of cart items for the specified cart ID")
  @GetMapping
  public ApiResponse<List<CartItemResponse>> getCartItemByCartId() {
    return ApiResponse.success("Get cart items by cart ID success", cartItemService.getCartItemByCartId());
  }

  @Operation(summary = "Add item to cart", description = "Add a new item to the cart")
  @PostMapping("/items")
  public ApiResponse<Boolean> addItemToCart(@RequestBody CartItemDTO cartRequest) {
    return ApiResponse.success("Add cart item success",cartItemService.addCartItem(cartRequest));
  }

  @Operation(summary = "Update cart item", description = "Update an existing item in the cart")
  @PatchMapping
  public ApiResponse<CartDTO> updateToCart(
      @Valid @RequestBody UpdateCartItemRequest updateCartItemRequest) {
    return ApiResponse.success("Cart update success !",cartItemService.updateCartItem(updateCartItemRequest));
  }

  @Operation(summary = "Delete cart item", description = "Delete an item from the cart by cart item ID")
  @DeleteMapping("/items/{cartItemId}")
  public ApiResponse<Boolean> deleteCartItem(@PathVariable  UUID cartItemId) {
    return ApiResponse.success("Delete cart item success",cartItemService.deleteCartItem(cartItemId));
  }

  @Operation(summary = "Delete all cart items", description = "Delete all items from the cart")
  @DeleteMapping
  public ApiResponse<Boolean> deleteAllCartItems() {
    return ApiResponse.success("Delete all cart items success",cartItemService.deleteAllCartItem());
  }
}
