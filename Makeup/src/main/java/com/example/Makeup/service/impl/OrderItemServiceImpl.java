package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.OrderItemDTO;
import com.example.Makeup.dto.request.OrderItemRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.Order;
import com.example.Makeup.entity.OrderItem;
import com.example.Makeup.entity.Product;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.enums.OrderItemStatus;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.mapper.OrderItemMapper;
import com.example.Makeup.repository.OrderItemRepository;
import com.example.Makeup.repository.OrderRepository;
import com.example.Makeup.repository.ProductRepository;
import com.example.Makeup.service.IOrderItemService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements IOrderItemService {
  private final CartItemServiceImpl cartItemService;
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final OrderItemMapper orderItemMapper;
  private final ProductRepository productRepository;

  @Override
  @Transactional
  public ApiResponse<String> createOrderItem(
      UUID orderId, List<OrderItemRequest> orderItemRequest) {

    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

    for (OrderItemRequest item : orderItemRequest) {
      Product product =
          productRepository
              .findById(item.getProductId())
              .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
      OrderItem orderItem = new OrderItem();
      orderItem.setPrice(item.getPrice());
      orderItem.setProduct(product);
      orderItem.setQuantity(item.getQuantity());
      orderItem.setRentalDate(item.getRentalDate().atStartOfDay());
      orderItem.setStatus(OrderItemStatus.PENDING);
      orderItem.setOrder(order);
      orderItemRepository.save(orderItem);
    }

    cartItemService.deleteAllCartItem();
    return ApiResponse.success("Order items created successfully", null);
  }

  @Override
  public ApiResponse<List<OrderItemDTO>> getOrderDetail(UUID orderId) {
    List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
    if (orderItems.isEmpty()) {
      throw new AppException(ErrorCode.ORDER_IS_EMPTY);
    }

    List<OrderItemDTO> orderItemDTOs =
        orderItems.stream().map(orderItemMapper::toOrderItemDTO).collect(Collectors.toList());
    return ApiResponse.success("Order items retrieved successfully", orderItemDTOs);
  }
}
