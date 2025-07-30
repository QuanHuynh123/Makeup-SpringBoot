package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.dto.request.OrderRequest;
import com.example.Makeup.dto.response.OrderItemDetailResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders/")
public class OrderRestController {

    private final IOrderService orderService;

    // Place an order
    @PostMapping("place")
    public ApiResponse<OrderDTO> placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping("my-order")
    public ApiResponse<List<OrderDTO>> getMyOrders() {
        return orderService.getMyOrders();
    }

    @GetMapping("{orderId}")
    public ApiResponse<OrderDTO> getOrder(@PathVariable("orderId") String orderId) {
        return orderService.getOrder(UUID.fromString(orderId));
    }

    @GetMapping("{orderId}/items")
    public ApiResponse<List<OrderItemDetailResponse>> getOderItems(@PathVariable("orderId") String orderId) {
        return orderService.getItemsDetail(UUID.fromString(orderId));
    }

    // Update order status ( deliver, payment , ... )
    @PutMapping("{orderId}/update-payment-status")
    public ApiResponse<String> updateStatusOrder(@PathVariable("orderId") String orderId) {
        int status = 0 ;
        return orderService.updateOrderStatus(UUID.fromString(orderId), status);
    }

    // Check condition for repayment
    @GetMapping("{orderId}/check-repayment")
    public ApiResponse<Boolean> checkRepaymentCondition(@PathVariable("orderId") String orderId) {
        return orderService.checkRepaymentCondition(UUID.fromString(orderId));
    }
}

