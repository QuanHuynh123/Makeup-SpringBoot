package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.service.CartItemService;
import com.example.Makeup.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    @GetMapping("")
    public String cart() {
        return "user/cart";
    }

    @GetMapping("/checkOut")
    public String checkOut() {
        return "user/checkOut";
    }
}
