package com.example.Makeup.controller.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/my-cart")
public class CartController {

  @GetMapping("")
  public String cart() {
    return "user/cart";
  }

  @GetMapping("/check-out")
  public String checkOut() {
    return "user/check-out";
  }
}
