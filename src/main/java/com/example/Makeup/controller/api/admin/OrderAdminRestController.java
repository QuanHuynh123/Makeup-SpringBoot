package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.response.OrdersAdminResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IOrderService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/orders")
public class OrderAdminRestController {
  @Autowired IOrderService orderService;

  @GetMapping
  public ApiResponse<Page<OrdersAdminResponse>> getOrders(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "sort", defaultValue = "orderDate,desc") String sort,
      @RequestParam(value = "status") Integer statusId) {
    Pageable pageable =
        PageRequest.of(
            page, size, Sort.by(Sort.Direction.fromString(sort.split(",")[1]), sort.split(",")[0]));
    return orderService.getAllOrder(pageable, statusId);
  }

  @PutMapping("{id}/update-status")
  public ApiResponse<String> updateStatusOrder(
      @PathVariable("id") String orderId, @RequestParam("status") int status) {
    return orderService.updateOrderStatus(UUID.fromString(orderId), status);
  }
}
