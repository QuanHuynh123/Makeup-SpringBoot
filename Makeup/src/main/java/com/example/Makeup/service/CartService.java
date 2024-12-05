package com.example.Makeup.service;

import com.example.Makeup.dto.CartDTO;
import com.example.Makeup.dto.CartItemDTO;
import com.example.Makeup.entity.Cart;
import com.example.Makeup.entity.CartItem;
import com.example.Makeup.entity.User;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.CartMapper;
import com.example.Makeup.repository.CartItemRepository;
import com.example.Makeup.repository.CartRepository;
import com.mysql.cj.Session;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    UserService userService;

    @Autowired
    CartItemRepository cartItemRepository;

    public CartDTO getCart (int userId){
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));
        return cartMapper.toCartDTO(cart);
    }

    public  void createCart(int userId){
        LocalDate localDate = LocalDate.now();

        Date sqlDate = Date.valueOf(localDate);
        User user = userService.getUserById(userId);
        Cart cart = new Cart( 0,0,sqlDate, user);
        cartRepository.save(cart);
    }

    public boolean updateCartTotals( int cartId){
        Cart cart  =  cartRepository.findById(cartId).orElseThrow(()->new AppException(ErrorCode.CANT_FOUND));;

        int quantityCart = 0;
        double totalPrice = 0 ;
        List<CartItem> cartItemList = cartItemRepository.findAllByCartId(cartId);
        if (cartItemList.isEmpty())
            throw  new AppException(ErrorCode.CANT_FOUND);
        else
            for (CartItem cartItem : cartItemList){
                quantityCart+=1;
                totalPrice += cartItem.getPrice();
            }
        cart.setTotalPrice(totalPrice);
        cart.setTotalQuantity(quantityCart);
        cartRepository.save(cart);
        return true;
    }
}
