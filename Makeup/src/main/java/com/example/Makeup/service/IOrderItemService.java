package com.example.Makeup.service;

import com.example.Makeup.dto.model.OrderItemDTO;
import com.example.Makeup.dto.request.OrderItemRequest;
import com.example.Makeup.dto.response.common.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface IOrderItemService {

    ApiResponse<String> createOrderItem(UUID orderId, List<OrderItemRequest> orderItemRequest);
    ApiResponse<List<OrderItemDTO>> getOrderDetail(UUID orderId);
}
