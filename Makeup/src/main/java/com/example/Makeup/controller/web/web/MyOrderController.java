package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.dto.model.OrderItemDTO;
import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.enums.ApiResponse;
import com.example.Makeup.service.OrderItemService;
import com.example.Makeup.service.OrderService;
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
public class MyOrderController {

    private final OrderItemService orderItemService;
    private final OrderService orderService;

    @GetMapping("/myOrder")
    public String myOrder(Model model, @ModelAttribute("user") UserDTO userDTO) {
        if (userDTO != null) {
            UUID userId = userDTO.getId();
            ApiResponse<List<OrderDTO>> orders = orderService.getMyOrder(userId);
            if (orders.getCode() == 200) {
                model.addAttribute("myOrder", orders.getResult());
            } else {
                log.warn("Failed to load orders for userId: {}", userId);
            }
        }
        return "/user/myOrder";
    }

    @GetMapping("/orderDetail/{orderId}")
    public String orderDetail(@PathVariable("orderId") UUID orderId , Model model){
        ApiResponse<List<OrderItemDTO>> orderItemDTOS = orderItemService.getOrderDetail(orderId);
        model.addAttribute("orderItems",orderItemDTOS.getResult());
        ApiResponse<OrderDTO> orderDTO = orderService.getOrder(orderId);
        model.addAttribute("order",orderDTO.getResult());
        return "/user/orderDetail";
    }
}
