package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.CartDTO;
import com.example.Makeup.entity.Cart;
import com.example.Makeup.entity.User;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-21T00:11:26+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public CartDTO toCartDTO(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        UUID userId = null;
        LocalDateTime createdAt = null;
        double totalPrice = 0.0d;
        int totalQuantity = 0;
        LocalDateTime updatedAt = null;
        UUID id = null;

        userId = cartUserId( cart );
        createdAt = cart.getCreatedAt();
        totalPrice = cart.getTotalPrice();
        totalQuantity = cart.getTotalQuantity();
        updatedAt = cart.getUpdatedAt();
        id = cart.getId();

        CartDTO cartDTO = new CartDTO( id, totalPrice, totalQuantity, userId, createdAt, updatedAt );

        return cartDTO;
    }

    @Override
    public Cart toCartEntity(CartDTO cartDTO) {
        if ( cartDTO == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setUser( cartDTOToUser( cartDTO ) );
        cart.setCreatedAt( cartDTO.getCreatedAt() );
        cart.setUpdatedAt( cartDTO.getUpdatedAt() );
        cart.setId( cartDTO.getId() );
        cart.setTotalPrice( cartDTO.getTotalPrice() );
        cart.setTotalQuantity( cartDTO.getTotalQuantity() );

        return cart;
    }

    private UUID cartUserId(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        User user = cart.getUser();
        if ( user == null ) {
            return null;
        }
        UUID id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected User cartDTOToUser(CartDTO cartDTO) {
        if ( cartDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( cartDTO.getUserId() );

        return user;
    }
}
