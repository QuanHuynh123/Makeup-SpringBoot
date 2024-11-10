package com.example.Makeup.mapper;

import com.example.Makeup.dto.CartItemDTO;
import com.example.Makeup.entity.Cart;
import com.example.Makeup.entity.CartItem;
import com.example.Makeup.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T16:42:42+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CartItemMapperImpl implements CartItemMapper {

    @Override
    public CartItemDTO toCartItemDTO(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemDTO cartItemDTO = new CartItemDTO();

        cartItemDTO.setCartId( cartItemCartId( cartItem ) );
        cartItemDTO.setProductId( cartItemProductId( cartItem ) );
        cartItemDTO.setId( cartItem.getId() );
        cartItemDTO.setQuantity( cartItem.getQuantity() );
        cartItemDTO.setPrice( cartItem.getPrice() );

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
        cartItem.setId( cartItemDTO.getId() );
        cartItem.setQuantity( cartItemDTO.getQuantity() );
        cartItem.setPrice( cartItemDTO.getPrice() );

        return cartItem;
    }

    private int cartItemCartId(CartItem cartItem) {
        if ( cartItem == null ) {
            return 0;
        }
        Cart cart = cartItem.getCart();
        if ( cart == null ) {
            return 0;
        }
        int id = cart.getId();
        return id;
    }

    private int cartItemProductId(CartItem cartItem) {
        if ( cartItem == null ) {
            return 0;
        }
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return 0;
        }
        int id = product.getId();
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
