package com.example.Makeup.controller.api.admin;

import com.example.Makeup.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order/")
public class OrderAdminRestController {
    @Autowired
    OrderService orderService;

    @PostMapping("/approve/{orderId}")
    @ResponseBody
    public ResponseEntity<String> approveOrder(@PathVariable("orderId") int orderId) {
        try {
            orderService.checkOrder(orderId);
            return ResponseEntity.ok("Approve successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("fail");
        }
    }
}
