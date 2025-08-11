package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.Cart;
import com.example.Makeup.entity.CartItem;
import com.example.Makeup.entity.User;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.mapper.CartMapper;
import com.example.Makeup.repository.CartItemRepository;
import com.example.Makeup.repository.CartRepository;
import com.example.Makeup.repository.UserRepository;
import com.example.Makeup.service.ICartService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

  private final CartRepository cartRepository;
  private final CartMapper cartMapper;
  private final UserRepository userRepository;
  private final CartItemRepository cartItemRepository;

  @Override
  public ApiResponse<CartDTO> getCart(UUID userId) {
    System.out.println("Get cart for user: " + userId);
    Cart cart =
        cartRepository
            .findByUserId(userId)
            .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
    return ApiResponse.success("Get cart success", cartMapper.toCartDTO(cart));
  }

  @Override
  @Transactional
  public ApiResponse<CartDTO> createCart(UUID accountId) {
    User user =
        userRepository
            .findByAccountId(accountId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    Cart cart = new Cart(null, 0, 0, user);
    cartRepository.save(cart);
    return ApiResponse.success("Create cart success", cartMapper.toCartDTO(cart));
  }

  @Transactional
  public CartDTO updateCartTotals(Cart cart) {

    List<CartItem> cartItemList = cartItemRepository.findAllByCartId(cart.getId());

    int quantityCart = 0;
    double totalPrice = 0;
    for (CartItem cartItem : cartItemList) {
      quantityCart += cartItem.getQuantity();
      totalPrice += cartItem.getPrice();
    }

    cart.setTotalPrice(totalPrice);
    cart.setTotalQuantity(quantityCart);
    return cartMapper.toCartDTO(cart);
  }

  public Cart checkCartAndUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new AppException(ErrorCode.USER_IN_AUTHENTICATED_NOT_FOUND);
    }

    Object principal = authentication.getPrincipal();
    if (!(principal instanceof UserDTO userDTO)) {
      throw new AppException(ErrorCode.USER_IN_AUTHENTICATED_NOT_FOUND);
    }

    UUID userId = userDTO.getId();
    Cart cart =
        cartRepository
            .findByUserId(userId)
            .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

    return cartRepository
        .findByIdForUpdate(cart.getId())
        .orElseThrow(() -> new AppException(ErrorCode.CART_IS_LOCKED_FOR_UPDATE));
  }

  public int countCartItem(UUID cartId) {
    return cartItemRepository.countByCartId(cartId);
  }
}
