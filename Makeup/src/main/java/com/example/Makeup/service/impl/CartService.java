package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.entity.Cart;
import com.example.Makeup.entity.CartItem;
import com.example.Makeup.entity.User;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.CartMapper;
import com.example.Makeup.repository.CartItemRepository;
import com.example.Makeup.repository.CartRepository;
import com.example.Makeup.repository.UserRepository;
import com.example.Makeup.service.ICartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;


    public ApiResponse<CartDTO> getCart (UUID userId){
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        return ApiResponse.success("Get cart success", cartMapper.toCartDTO(cart));
    }

    @Transactional
    public ApiResponse<CartDTO> createCart(UUID accountId){
        LocalDateTime localDate = LocalDateTime.now();

        User user = userRepository.findByAccountId(accountId);
        Cart cart = new Cart(null,0,0, user);
        cartRepository.save(cart);
        return ApiResponse.success("Create cart success", cartMapper.toCartDTO(cart));
    }

    @Transactional
    public ApiResponse<CartDTO> updateCartTotals( UUID cartId){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        if (!checkCartAndUser(cartId)) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }

        List<CartItem> cartItemList = cartItemRepository.findAllByCartId(cartId);

        int quantityCart = 0;
        double totalPrice = 0;
        for (CartItem cartItem : cartItemList) {
            quantityCart += cartItem.getQuantity();
            totalPrice += cartItem.getPrice();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalQuantity(quantityCart);
        return ApiResponse.success("Update cart totals success", cartMapper.toCartDTO(cart));
    }

    public boolean checkCartAndUser(UUID cartId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDTO userDTO)) {
            return false;
        }

        UUID userId = userDTO.getId();
        return cartRepository.existsByIdAndUserId(cartId, userId);
    }

    public int countCartItem(UUID cartId){
        return cartItemRepository.countByCartId(cartId);
    }

}
