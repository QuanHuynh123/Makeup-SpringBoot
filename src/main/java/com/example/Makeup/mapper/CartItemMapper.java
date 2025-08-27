package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.CartItemDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
  @Mapping(source = "cart.id", target = "cartId")
  @Mapping(source = "product.id", target = "productId")
  CartItemDTO toCartItemDTO(CartItem cartItem);

  @Mapping(source = "cartId", target = "cart.id")
  @Mapping(source = "productId", target = "product.id")
  CartItem toCartItemEntity(CartItemDTO cartItemDTO);
}
