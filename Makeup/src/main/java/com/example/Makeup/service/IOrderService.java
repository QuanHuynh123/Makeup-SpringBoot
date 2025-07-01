package com.example.Makeup.service;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.dto.response.common.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface IOrderService {

    ApiResponse<OrderDTO> createOrder(String email, String firstName, String phoneNumber, String message, int paymentMethod, int quantity, double totalPrice);
    ApiResponse<OrderDTO> getOrder(UUID orderId);
    ApiResponse<Boolean> checkOrder(UUID orderId);
    ApiResponse<List<OrderDTO>> getAllApproveOrder();
    ApiResponse<List<OrderDTO>> getAllOrder();
    ApiResponse<Boolean> returnProductOfOrder(UUID orderId);
    ApiResponse<List<OrderDTO>> getMyOrder(UUID userId);
}
