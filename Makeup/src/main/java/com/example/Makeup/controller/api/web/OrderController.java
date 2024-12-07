package com.example.Makeup.controller.api.web;

import com.example.Makeup.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/place")
    @ResponseBody
    public ResponseEntity<String> placeOrder(@RequestParam String email,
                                             @RequestParam String firstName,
                                             @RequestParam String phoneNumber,
                                             @RequestParam String message,
                                             @RequestParam int paymentMethod,
                                             @RequestParam int quantity,
                                             @RequestParam double totalPrice) {
        System.out.println(email + " / " + paymentMethod + " / " + quantity + " / " + totalPrice);
        return ResponseEntity.ok("Order placed successfully!");
    }
}

