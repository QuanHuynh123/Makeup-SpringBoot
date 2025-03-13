package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.CartItemDTO;
import com.example.Makeup.service.CartItemService;
import com.example.Makeup.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        Integer cartId = (Integer) session.getAttribute("cartId");

        if (cartId == null) {
            return ResponseEntity.noContent().build();
        }
        if(cartItemService.addCartItem(cartRequest,cartId))
            return  ResponseEntity.ok("");
        else return ResponseEntity.badRequest().body("");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateToCart(@RequestBody CartItemDTO cartRequest , HttpSession session)
    {
        try{
            int cartId = (int) session.getAttribute("cartId");
            cartItemService.updateCartItem(cartRequest,cartId);
            return  ResponseEntity.ok("");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteToCart(@RequestParam int cartItemId, HttpSession session)
    {
        try{
            int cartId = (int) session.getAttribute("cartId");
            cartItemService.deleteCartItem(cartItemId,cartId);
            return  ResponseEntity.ok("");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("");
        }
    }
}
