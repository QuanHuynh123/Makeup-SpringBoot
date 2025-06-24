package com.example.Makeup.service;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.dto.model.CartItemDTO;
import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.entity.CartItem;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.CartItemMapper;
import com.example.Makeup.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final CartItemMapper cartItemMapper;
    private final ProductService productService;


    public ApiResponse<List<CartItemDTO>> getCartItemByCartId(UUID cartId) {
        if (!cartService.checkCartAndUser(cartId)) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        List<CartItem> cartItem = cartItemRepository.findAllByCartId(cartId);
        if (cartItem.isEmpty()) {
            throw new AppException(ErrorCode.CART_IS_EMPTY);
        }
        return ApiResponse.<List<CartItemDTO>>builder()
                .code(200)
                .message("Cart item list")
                .result(cartItem.stream()
                        .map(cartItemMapper::toCartItemDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public ApiResponse<Boolean> addCartItem(CartItemDTO cartItemDTO, UUID cartId) {
        if (!cartService.checkCartAndUser(cartId)) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        ProductDTO productDTO = productService.findProductById(cartItemDTO.getProductId()).getResult();
        cartItemDTO.setPrice(productDTO.getPrice() * cartItemDTO.getQuantity());
        cartItemDTO.setCartId(cartId);
        cartItemRepository.save(cartItemMapper.toCartItemEntity(cartItemDTO));

        return ApiResponse.<Boolean>builder()
                .code(200)
                .message("Add cart item success")
                .result(true)
                .build();
    }

    @Transactional
    public ApiResponse<Boolean> deleteCartItem(UUID cartItemId, UUID cartId) {
        if (!cartService.checkCartAndUser(cartId)) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_IS_EMPTY));
        cartItemRepository.delete(cartItem);
        return ApiResponse.<Boolean>builder()
                .code(200)
                .message("Delete cart item success")
                .result(true)
                .build();
    }

    @Transactional
    public ApiResponse<Boolean> deleteAllCartItem(UUID cartId) {
        if (!cartService.checkCartAndUser(cartId)) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        int check = cartItemRepository.deleteAllByCartId(cartId);
        if (check == 0) {
            throw new AppException(ErrorCode.CART_ITEM_DELETE_FAILED);
        }
        return ApiResponse.<Boolean>builder()
                .code(200)
                .message("Delete all cart item success")
                .result(true)
                .build();
    }

    @Transactional
    public ApiResponse<CartDTO> updateCartItem(CartItemDTO cartItemDTO, UUID cartId) {
        if (!cartService.checkCartAndUser(cartId)) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        ApiResponse<ProductDTO> productDTO = productService.findProductById(cartItemDTO.getProductId());

        CartItem existingCartItem = cartItemRepository.findById(cartItemDTO.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_IS_EMPTY));
        existingCartItem.setQuantity(cartItemDTO.getQuantity());
        existingCartItem.setPrice(productDTO.getResult().getPrice() * cartItemDTO.getQuantity());
        existingCartItem.setRentalDate( cartItemDTO.getRentalDate());
        cartItemRepository.save(existingCartItem);
        return cartService.updateCartTotals(cartId);
    }
}
