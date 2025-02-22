package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.OrderDTO;
import com.example.Makeup.dto.OrderItemDTO;
import com.example.Makeup.service.OrderItemService;
import com.example.Makeup.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/")
public class MyOrderController {
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;
    @GetMapping("myOrder")
    public String myOrder(){
        return "/user/myOrder";
    }

    @GetMapping("orderDetail/{orderId}")
    public String orderDetail(@PathVariable("orderId") int orderId , Model model){
        List<OrderItemDTO> orderItemDTOS = orderItemService.getOrderDetail(orderId);
        model.addAttribute("orderItems",orderItemDTOS);
        OrderDTO orderDTO = orderService.getOrder(orderId);
        model.addAttribute("order",orderDTO);
        return "/user/orderDetail";
    }
}
