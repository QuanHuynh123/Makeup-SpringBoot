package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.dto.request.OrderRequest;
import com.example.Makeup.dto.response.OrderItemDetailResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IOrderService;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order API", description = "API for order operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders/")
public class OrderRestController {

  private final IOrderService orderService;

  // Place an order
  @Operation(summary = "Place an order", description = "Create a new order with the provided order details")
  @PostMapping("place")
  public ApiResponse<OrderDTO> placeOrder(@RequestBody OrderRequest orderRequest) {
    return ApiResponse.success("Create order successfully",orderService.createOrder(orderRequest));
  }

  @Operation(summary = "Get my orders", description = "Retrieve a list of my orders")
  @GetMapping("my-order")
  public ApiResponse<List<OrderDTO>> getMyOrders() {
    return ApiResponse.success("Get my-order success",orderService.getMyOrders());
  }

  @Operation(summary = "Get order by ID", description = "Retrieve order details by order ID")
  @GetMapping("{orderId}")
  public ApiResponse<OrderDTO> getOrder(@PathVariable("orderId") String orderId) {
    return ApiResponse.success("Get order successfully",orderService.getOrder(UUID.fromString(orderId)));
  }

  @Operation(summary = "Get order items detail", description = "Retrieve order items detail by order ID")
  @GetMapping("{orderId}/items")
  public ApiResponse<List<OrderItemDetailResponse>> getOderItems(
      @PathVariable("orderId") String orderId) {
    return ApiResponse.success("Get order items detail successfully",orderService.getItemsDetail(UUID.fromString(orderId)));
  }

  // Update order status ( deliver, payment , ... )
  @Operation(summary = "Update order payment status", description = "Update the payment status of an order by order ID")
  @PutMapping("{orderId}/update-payment-status")
  public ApiResponse<String> updateStatusOrder(@PathVariable("orderId") String orderId) {
    int status = 0;
    return ApiResponse.success("Update order status successfully",orderService.updateOrderStatus(UUID.fromString(orderId), status));
  }

  // Check condition for repayment
  @Operation(summary = "Check repayment condition", description = "Check if the order meets the conditions for repayment by order ID")
  @GetMapping("{orderId}/check-repayment")
  public ApiResponse<Boolean> checkRepaymentCondition(@PathVariable("orderId") String orderId) {
    return ApiResponse.success("Check repayment condition successfully", orderService.checkRepaymentCondition(UUID.fromString(orderId)));
  }
}
