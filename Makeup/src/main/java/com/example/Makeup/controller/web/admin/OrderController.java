package com.example.Makeup.controller.web.admin;

import com.example.Makeup.dto.OrderDTO;
import com.example.Makeup.service.OrderItemService;
import com.example.Makeup.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @GetMapping("order")
    public String homeOrder(Model model){
        List<OrderDTO> orderDTOS = orderService.getAllOrder();
        model.addAttribute("orders",orderDTOS);
        return "admin/orders";
    }

    @GetMapping("approveOrder")
    public String homeApproveOrder(Model model){
        List<OrderDTO> orderDTOS = orderService.getAllApproveOrder();
        model.addAttribute("orders",orderDTOS);
        return "admin/approveOrders";
    }
}
