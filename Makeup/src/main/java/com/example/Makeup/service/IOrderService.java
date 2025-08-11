package com.example.Makeup.service;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.dto.request.OrderRequest;
import com.example.Makeup.dto.response.OrderItemDetailResponse;
import com.example.Makeup.dto.response.OrdersAdminResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {

  ApiResponse<OrderDTO> createOrder(OrderRequest orderRequest);

  ApiResponse<OrderDTO> getOrder(UUID orderId);

  ApiResponse<Boolean> checkOrder(UUID orderId);

  ApiResponse<Page<OrdersAdminResponse>> getAllOrder(Pageable pageable, Integer status);

  ApiResponse<Boolean> returnProductOfOrder(UUID orderId);

  ApiResponse<List<OrderDTO>> getMyOrders();

  ApiResponse<List<OrderItemDetailResponse>> getItemsDetail(UUID orderId);

  ApiResponse<String> updateOrderStatus(UUID orderId, int status);

  ApiResponse<Boolean> checkRepaymentCondition(UUID orderId);

  ApiResponse<Void> clearOrderPaymentExpired();
}
