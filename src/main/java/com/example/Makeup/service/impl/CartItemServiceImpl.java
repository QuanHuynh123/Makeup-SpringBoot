package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.dto.model.CartItemDTO;
import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.request.UpdateCartItemRequest;
import com.example.Makeup.dto.response.CartItemResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.Cart;
import com.example.Makeup.entity.CartItem;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.mapper.CartItemMapper;
import com.example.Makeup.repository.CartItemRepository;
import com.example.Makeup.service.ICartItemService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements ICartItemService {

  private final CartItemRepository cartItemRepository;
  private final CartServiceImpl cartServiceImpl;
  private final CartItemMapper cartItemMapper;
  private final ProductServiceImpl productServiceImpl;

  @Override
  @Transactional
  public List<CartItemResponse> getCartItemByCartId() {
    Cart cart = cartServiceImpl.checkCartAndUser();
    List<CartItemResponse> cartItems = cartItemRepository.findAllCartItem(cart.getId());
    if (cartItems.isEmpty()) {
      throw new AppException(ErrorCode.CART_IS_EMPTY);
    }

      return cartItems.stream()
          .map(
              cartItem ->
                  new CartItemResponse(
                      cartItem.getId(),
                      cartItem.getQuantity(),
                      cartItem.getPrice(),
                      cartItem.getRentalDate(),
                      cartItem.getCartId(),
                      cartItem.getProductId(),
                      cartItem.getProductName(),
                      cartItem.getFirstImage().split(",")[0],
                      cartItem.getCreatedAt(),
                      cartItem.getUpdatedAt()))
          .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public Boolean addCartItem(CartItemDTO cartItemDTO) {
    Cart cart = cartServiceImpl.checkCartAndUser();

    ProductDTO productDTO =
        productServiceImpl.findProductById(cartItemDTO.getProductId());
    cartItemDTO.setPrice(productDTO.getPrice() * cartItemDTO.getQuantity());
    cartItemDTO.setCartId(cart.getId());
    cartItemRepository.saveAndFlush(cartItemMapper.toCartItemEntity(cartItemDTO));
    cartServiceImpl.updateCartTotals(cart);
    return  true;
  }

  @Override
  @Transactional
  public Boolean deleteCartItem(UUID cartItemId) {
    Cart cart = cartServiceImpl.checkCartAndUser();
    CartItem cartItem =
        cartItemRepository
            .findById(cartItemId)
            .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

    cartItemRepository.delete(cartItem);
    cartItemRepository.flush();
    cartServiceImpl.updateCartTotals(cart);
    return true;
  }

  @Override
  @Transactional
  public CartDTO updateCartItem(UpdateCartItemRequest cartItem) {
    Cart cart = cartServiceImpl.checkCartAndUser();
    ProductDTO productDTO =
        productServiceImpl.findProductById(cartItem.getProductId());

    CartItem existingCartItem =
        cartItemRepository
            .findById(cartItem.getCartItemId())
            .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
    existingCartItem.setQuantity(cartItem.getQuantity());
    existingCartItem.setPrice(productDTO.getPrice() * cartItem.getQuantity());
    existingCartItem.setRentalDate(cartItem.getRentalDate());
    cartItemRepository.saveAndFlush(existingCartItem);
    return cartServiceImpl.updateCartTotals(cart);
  }

  @Override
  @Transactional
  public Boolean deleteAllCartItem() {
    Cart cart = cartServiceImpl.checkCartAndUser();
    List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());
    if (cartItems.isEmpty()) {
      throw new AppException(ErrorCode.CART_IS_EMPTY);
    }
    cartItemRepository.deleteAll(cartItems);
    cartItemRepository.flush();
    cartServiceImpl.updateCartTotals(cart);
    return true;
  }
}
