package com.example.Makeup.service;

import com.example.Makeup.dto.CartItemDTO;
import com.example.Makeup.entity.CartItem;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.CartItemMapper;
import com.example.Makeup.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartItemMapper cartItemMapper;

    public List<CartItemDTO> getCartItemByCartId(int cartId){
        List<CartItem> cartItem = cartItemRepository.findAllByCartId(cartId);

        return cartItem.stream()
                .map(cartItemMapper::toCartItemDTO)
                .collect(Collectors.toList());
    }
}
