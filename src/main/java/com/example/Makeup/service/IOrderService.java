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

  OrderDTO createOrder(OrderRequest orderRequest);

  OrderDTO getOrder(UUID orderId);

  Boolean checkOrder(UUID orderId);

  Page<OrdersAdminResponse> getAllOrder(Pageable pageable, Integer status);

  Boolean returnProductOfOrder(UUID orderId);

  List<OrderDTO> getMyOrders();

  List<OrderItemDetailResponse> getItemsDetail(UUID orderId);

  String updateOrderStatus(UUID orderId, int status);

  Boolean checkRepaymentCondition(UUID orderId);

  void clearOrderPaymentExpired();
}
