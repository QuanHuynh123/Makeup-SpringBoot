package com.example.Makeup.controller.api.web;

import com.example.Makeup.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/cart")
public class CartRestController {

    @Autowired
    CartService cartService;


    @GetMapping("/add/{idProduct}")
    public ResponseEntity<> addCartItem()
    
}
