package com.example.Makeup.mapper;

import com.example.Makeup.dto.*;
import com.example.Makeup.entity.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface CartItemMapper {
    @Mapping(source = "cart.id", target = "cartId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.nameProduct", target = "productName")
    @Mapping(source = "product.image", target="imageFirst")
    CartItemDTO toCartItemDTO(CartItem cartItem);

    @Mapping(source = "cartId", target = "cart.id")
    @Mapping(source = "productId", target = "product.id")
    CartItem toCartItemEntity(CartItemDTO cartItemDTO);

    @AfterMapping
    default void setImageFirst(@MappingTarget CartItemDTO cartItemDTO, CartItem cartItem) {
        if (cartItem.getProduct() != null &&
                cartItem.getProduct().getImage() != null &&
                !cartItem.getProduct().getImage().isEmpty()) {
            // Tách chuỗi image theo dấu ','
            String[] images = cartItem.getProduct().getImage().split(",");
            // Lấy giá trị đầu tiên
            cartItemDTO.setImageFirst(images[0].trim());
        }
    }

}
