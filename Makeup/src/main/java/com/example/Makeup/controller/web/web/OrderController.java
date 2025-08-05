package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.dto.model.OrderItemDTO;
import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IOrderItemService;
import com.example.Makeup.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderItemService orderItemService;
    private final IOrderService orderService;

    @GetMapping("/my-order")
    public String myOrder() {
        return "user/my-order";
    }

    @GetMapping("/orderDetail/{orderId}")
    public String orderDetail(@PathVariable("orderId") UUID orderId , Model model){
        ApiResponse<List<OrderItemDTO>> orderItemDTOS = orderItemService.getOrderDetail(orderId);
        model.addAttribute("orderItems",orderItemDTOS.getResult());
        ApiResponse<OrderDTO> orderDTO = orderService.getOrder(orderId);
        model.addAttribute("order",orderDTO.getResult());
        return "user/orderDetail";
    }
}
