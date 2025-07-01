package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IOrderService;
import com.example.Makeup.service.impl.OrderItemService;
import com.example.Makeup.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("api/order/")
public class OrderRestController {

    private final IOrderService orderService;

    @PostMapping("place")
    @ResponseBody
    public ApiResponse<OrderDTO> placeOrder(@RequestParam String email,
                                            @RequestParam String firstName,
                                            @RequestParam String phoneNumber,
                                            @RequestParam String message,
                                            @RequestParam int quantity,
                                            @RequestParam double amount) {
        return orderService.createOrder(email,firstName,phoneNumber,message,1,quantity,amount);

    }
}

