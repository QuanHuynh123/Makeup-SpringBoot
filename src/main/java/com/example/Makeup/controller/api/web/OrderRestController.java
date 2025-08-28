package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.dto.request.OrderRequest;
import com.example.Makeup.dto.response.OrderItemDetailResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IOrderService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders/")
public class OrderRestController {

  private final IOrderService orderService;

  // Place an order
  @PostMapping("place")
  public ApiResponse<OrderDTO> placeOrder(@RequestBody OrderRequest orderRequest) {
    return ApiResponse.success("Create order successfully",orderService.createOrder(orderRequest));
  }

  @GetMapping("my-order")
  public ApiResponse<List<OrderDTO>> getMyOrders() {
    return ApiResponse.success("Get my-order success",orderService.getMyOrders());
  }

  @GetMapping("{orderId}")
  public ApiResponse<OrderDTO> getOrder(@PathVariable("orderId") String orderId) {
    return ApiResponse.success("Get order successfully",orderService.getOrder(UUID.fromString(orderId)));
  }

  @GetMapping("{orderId}/items")
  public ApiResponse<List<OrderItemDetailResponse>> getOderItems(
      @PathVariable("orderId") String orderId) {
    return ApiResponse.success("Get order items detail successfully",orderService.getItemsDetail(UUID.fromString(orderId)));
  }

  // Update order status ( deliver, payment , ... )
  @PutMapping("{orderId}/update-payment-status")
  public ApiResponse<String> updateStatusOrder(@PathVariable("orderId") String orderId) {
    int status = 0;
    return ApiResponse.success("Update order status successfully",orderService.updateOrderStatus(UUID.fromString(orderId), status));
  }

  // Check condition for repayment
  @GetMapping("{orderId}/check-repayment")
  public ApiResponse<Boolean> checkRepaymentCondition(@PathVariable("orderId") String orderId) {
    return ApiResponse.success("Check repayment condition successfully", orderService.checkRepaymentCondition(UUID.fromString(orderId)));
  }
}
