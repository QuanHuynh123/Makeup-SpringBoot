package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.CartItemDTO;
import com.example.Makeup.service.CartItemService;
import com.example.Makeup.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("api/cart")
public class CartRestController {

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItemDTO cartRequest , HttpSession session)
    {
        UUID cartId = (UUID) session.getAttribute("cartId");
        if(cartItemService.addCartItem(cartRequest,cartId).getResult())
            return  ResponseEntity.ok("");
        else return ResponseEntity.badRequest().body("");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateToCart(@RequestBody CartItemDTO cartRequest , HttpSession session)
    {
        try{
            UUID cartId = (UUID) session.getAttribute("cartId");
            cartItemService.updateCartItem(cartRequest,cartId);
            return  ResponseEntity.ok("");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteToCart(@RequestParam UUID cartItemId, HttpSession session)
    {
        try{
            UUID cartId = (UUID) session.getAttribute("cartId");
            cartItemService.deleteCartItem(cartItemId,cartId);
            return  ResponseEntity.ok("");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("");
        }
    }
}
