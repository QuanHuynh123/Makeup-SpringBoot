package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.dto.model.CartItemDTO;
import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.entity.CartItem;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.CartItemMapper;
import com.example.Makeup.repository.CartItemRepository;
import com.example.Makeup.service.ICartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final CartItemMapper cartItemMapper;
    private final ProductService productService;

    @Override
    public ApiResponse<List<CartItemDTO>> getCartItemByCartId(UUID cartId) {
        if (!cartService.checkCartAndUser(cartId)) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        List<CartItem> cartItem = cartItemRepository.findAllByCartId(cartId);
        if (cartItem.isEmpty()) {
            throw new AppException(ErrorCode.CART_IS_EMPTY);
        }

        List<CartItemDTO> cartItemDTOs = cartItem.stream()
                .map(cartItemMapper::toCartItemDTO)
                .collect(Collectors.toList());
        return ApiResponse.success("Get cart items by cart ID success", cartItemDTOs);
    }

    @Override
    @Transactional
    public ApiResponse<Boolean> addCartItem(CartItemDTO cartItemDTO, UUID cartId) {
        if (!cartService.checkCartAndUser(cartId)) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        ProductDTO productDTO = productService.findProductById(cartItemDTO.getProductId()).getResult();
        cartItemDTO.setPrice(productDTO.getPrice() * cartItemDTO.getQuantity());
        cartItemDTO.setCartId(cartId);
        cartItemRepository.save(cartItemMapper.toCartItemEntity(cartItemDTO));

        return ApiResponse.success("Add cart item success", true);
    }

    @Override
    @Transactional
    public ApiResponse<Boolean> deleteCartItem(UUID cartItemId, UUID cartId) {
        if (!cartService.checkCartAndUser(cartId)) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_IS_EMPTY));
        cartItemRepository.delete(cartItem);
        return ApiResponse.success("Delete cart item success", true);
    }

    @Override
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
