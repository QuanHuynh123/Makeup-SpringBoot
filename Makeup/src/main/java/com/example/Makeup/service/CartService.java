package com.example.Makeup.service;

import com.example.Makeup.dto.CartDTO;
import com.example.Makeup.entity.Cart;
import com.example.Makeup.entity.User;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.CartMapper;
import com.example.Makeup.repository.CartRepository;
import com.mysql.cj.Session;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    UserService userService;

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
}
