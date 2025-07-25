package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.response.OrdersAdminResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.enums.OrderStatus;
import com.example.Makeup.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/orders")
public class OrderAdminRestController {
    @Autowired
    IOrderService orderService;

    @GetMapping
    public ApiResponse<Page<OrdersAdminResponse>> getOrders(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "orderDate,desc") String sort,
            @RequestParam(value = "status", defaultValue = "0") Integer statusId
    ) {
        OrderStatus status = OrderStatus.fromId(statusId);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort.split(",")[1]), sort.split(",")[0]));
        System.out.println("Fetching orders with pageable: " + pageable + " and status: " + status);
        return orderService.getAllOrder(pageable, status);
    }


//    @PostMapping("/approve/{orderId}")
//    @ResponseBody
//    public ResponseEntity<String> approveOrder(@PathVariable("orderId") UUID orderId) {
//        try {
//            orderService.checkOrder(orderId);
//            return ResponseEntity.ok("Approve successfully!");
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body("fail");
//        }
//    }

}
