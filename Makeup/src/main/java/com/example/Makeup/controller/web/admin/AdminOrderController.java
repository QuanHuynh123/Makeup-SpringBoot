package com.example.Makeup.controller.web.admin;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminOrderController {

    private final IOrderService orderService;

    @GetMapping("order")
    public String homeOrder(Model model){
        ApiResponse<List<OrderDTO>> orderDTOS = orderService.getAllOrder();
        model.addAttribute("orders",orderDTOS.getResult());
        return "admin/orders";
    }

    @GetMapping("approveOrder")
    public String homeApproveOrder(Model model){
        ApiResponse<List<OrderDTO>> orderDTOS = orderService.getAllApproveOrder();
        model.addAttribute("orders",orderDTOS.getResult());
        return "admin/approveOrders";
    }
}
