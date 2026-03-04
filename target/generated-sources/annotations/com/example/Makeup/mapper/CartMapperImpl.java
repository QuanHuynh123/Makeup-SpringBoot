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
    date = "2025-10-20T22:10:21+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public CartDTO toCartDTO(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        UUID userId = null;
        double totalPrice = 0.0d;
        int totalQuantity = 0;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        UUID id = null;

        userId = cartUserId( cart );
        totalPrice = cart.getTotalPrice();
        totalQuantity = cart.getTotalQuantity();
        createdAt = cart.getCreatedAt();
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
