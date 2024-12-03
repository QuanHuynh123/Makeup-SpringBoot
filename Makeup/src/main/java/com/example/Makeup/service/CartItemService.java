package com.example.Makeup.service;

import com.example.Makeup.dto.CartItemDTO;
import com.example.Makeup.dto.ProductDTO;
import com.example.Makeup.entity.CartItem;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.CartItemMapper;
import com.example.Makeup.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemMapper cartItemMapper;

    @Autowired
    ProductService productService;

    public List<CartItemDTO> getCartItemByCartId(int cartId){
        List<CartItem> cartItem = cartItemRepository.findAllByCartId(cartId);

        return cartItem.stream()
                .map(cartItemMapper::toCartItemDTO)
                .collect(Collectors.toList());
    }

    public boolean addCartItem ( CartItemDTO cartItemDTO , int cartId){
        ProductDTO productDTO = productService.findById(cartItemDTO.getProductId());
        cartItemDTO.setPrice(productDTO.getPrice() * cartItemDTO.getQuantity());
        cartItemDTO.setCartId(cartId);
        cartItemRepository.save(cartItemMapper.toCartItemEntity(cartItemDTO));

        return cartService.updateCartTotals( cartId);
    }

    public boolean deleteCartItem(int cartItemId , int cartId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));
        cartItemRepository.delete(cartItem);
        return cartService.updateCartTotals(cartId);
    }

    public boolean updateCartItem(CartItemDTO cartItemDTO, int cartId){
        ProductDTO productDTO = productService.findById(cartItemDTO.getProductId());

        CartItem existingCartItem = cartItemRepository.findById(cartItemDTO.getCartItemId())
                .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));
        existingCartItem.setQuantity(cartItemDTO.getQuantity());
        existingCartItem.setPrice(productDTO.getPrice() * cartItemDTO.getQuantity());
        existingCartItem.setUseDate((Date) cartItemDTO.getUseDate());
        cartItemRepository.save(existingCartItem);
        return cartService.updateCartTotals(cartId);
    }
}
