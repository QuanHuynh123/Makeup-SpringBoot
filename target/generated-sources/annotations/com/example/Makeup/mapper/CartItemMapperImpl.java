package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.CartItemDTO;
import com.example.Makeup.entity.Cart;
import com.example.Makeup.entity.CartItem;
import com.example.Makeup.entity.Product;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-01T21:04:50+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class CartItemMapperImpl implements CartItemMapper {

    @Override
    public CartItemDTO toCartItemDTO(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        UUID cartId = null;
        UUID productId = null;
        LocalDateTime createdAt = null;
        double price = 0.0d;
        int quantity = 0;
        LocalDateTime rentalDate = null;
        LocalDateTime updatedAt = null;
        UUID id = null;

        cartId = cartItemCartId( cartItem );
        productId = cartItemProductId( cartItem );
        createdAt = cartItem.getCreatedAt();
        price = cartItem.getPrice();
        quantity = cartItem.getQuantity();
        rentalDate = cartItem.getRentalDate();
        updatedAt = cartItem.getUpdatedAt();
        id = cartItem.getId();

        CartItemDTO cartItemDTO = new CartItemDTO( id, quantity, price, rentalDate, cartId, productId, createdAt, updatedAt );

        return cartItemDTO;
    }

    @Override
    public CartItem toCartItemEntity(CartItemDTO cartItemDTO) {
        if ( cartItemDTO == null ) {
            return null;
        }

        CartItem cartItem = new CartItem();

        cartItem.setCart( cartItemDTOToCart( cartItemDTO ) );
        cartItem.setProduct( cartItemDTOToProduct( cartItemDTO ) );
        cartItem.setCreatedAt( cartItemDTO.getCreatedAt() );
        cartItem.setUpdatedAt( cartItemDTO.getUpdatedAt() );
        cartItem.setId( cartItemDTO.getId() );
        cartItem.setPrice( cartItemDTO.getPrice() );
        cartItem.setQuantity( cartItemDTO.getQuantity() );
        cartItem.setRentalDate( cartItemDTO.getRentalDate() );

        return cartItem;
    }

    private UUID cartItemCartId(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }
        Cart cart = cartItem.getCart();
        if ( cart == null ) {
            return null;
        }
        UUID id = cart.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private UUID cartItemProductId(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        UUID id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Cart cartItemDTOToCart(CartItemDTO cartItemDTO) {
        if ( cartItemDTO == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setId( cartItemDTO.getCartId() );

        return cart;
    }

    protected Product cartItemDTOToProduct(CartItemDTO cartItemDTO) {
        if ( cartItemDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( cartItemDTO.getProductId() );

        return product;
    }
}
