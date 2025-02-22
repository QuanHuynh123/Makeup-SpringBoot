package com.example.Makeup.controller.api.web;

import com.example.Makeup.service.CartItemService;
import com.example.Makeup.service.OrderItemService;
import com.example.Makeup.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/order/")
public class OrderRestController {

    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;

    @PostMapping("place")
    @ResponseBody
    public ResponseEntity<String> placeOrder(@RequestParam String email,
                                             @RequestParam String firstName,
                                             @RequestParam String phoneNumber,
                                             @RequestParam String message,
                                             @RequestParam int quantity,
                                             @RequestParam double amount,
                                             HttpSession httpSession) {
        try {
            System.out.println(email + " / " + 1 + " / " + quantity + " / " + amount);
            int orderId = orderService.createOrder(email,firstName,phoneNumber,message,1,quantity,amount);
            orderItemService.createOrderItem((Integer) httpSession.getAttribute("cartId"),orderId);
            return ResponseEntity.ok("Order placed successfully!");
        }catch (Exception e){
            System.out.println("fail order");
            return ResponseEntity.badRequest().body("fail order");
        }
    }
}

