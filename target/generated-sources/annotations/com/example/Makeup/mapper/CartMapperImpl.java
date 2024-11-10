package com.example.Makeup.mapper;

import com.example.Makeup.dto.CartDTO;
import com.example.Makeup.entity.Cart;
import com.example.Makeup.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T18:08:01+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public CartDTO toCartDTO(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();

        cartDTO.setUserId( cartUserId( cart ) );
        cartDTO.setId( cart.getId() );
        cartDTO.setTotalPrice( cart.getTotalPrice() );
        cartDTO.setTotalQuantity( cart.getTotalQuantity() );
        cartDTO.setCreateDate( cart.getCreateDate() );

        return cartDTO;
    }

    @Override
    public Cart toCartEntity(CartDTO cartDTO) {
        if ( cartDTO == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setUser( cartDTOToUser( cartDTO ) );
        cart.setId( cartDTO.getId() );
        cart.setTotalPrice( cartDTO.getTotalPrice() );
        cart.setTotalQuantity( cartDTO.getTotalQuantity() );
        cart.setCreateDate( cartDTO.getCreateDate() );

        return cart;
    }

    private int cartUserId(Cart cart) {
        if ( cart == null ) {
            return 0;
        }
        User user = cart.getUser();
        if ( user == null ) {
            return 0;
        }
        int id = user.getId();
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
