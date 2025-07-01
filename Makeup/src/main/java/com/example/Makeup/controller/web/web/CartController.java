package com.example.Makeup.controller.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/myCart")
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
