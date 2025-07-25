package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.dto.model.CartItemDTO;
import com.example.Makeup.dto.request.UpdateCartItemRequest;
import com.example.Makeup.dto.response.CartItemResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CartRestController {

    private final ICartItemService cartItemService;

    @GetMapping()
    public ApiResponse<List<CartItemResponse>> getCartItemByCartId()
    {
        return cartItemService.getCartItemByCartId();
    }

    @PostMapping("/add")
    public ApiResponse<Boolean> addItemToCart(@RequestBody CartItemDTO cartRequest)
    {
        return cartItemService.addCartItem(cartRequest);
    }

    @PostMapping("/update")
    public ApiResponse<CartDTO> updateToCart(@RequestBody UpdateCartItemRequest updateCartItemRequest )
    {
        return cartItemService.updateCartItem(updateCartItemRequest);
    }

    @DeleteMapping("/delete")
    public ApiResponse<Boolean> deleteToCart(@RequestParam UUID cartItemId)
    {
           return  cartItemService.deleteCartItem(cartItemId);
    }

    @DeleteMapping("/delete-all")
    public ApiResponse<Boolean> deleteAllToCart()
    {
        return cartItemService.deleteAllCartItem();
    }
}