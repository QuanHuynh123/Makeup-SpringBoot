package com.example.Makeup.service;

import com.example.Makeup.dto.model.OrderItemDTO;
import com.example.Makeup.entity.CartItem;
import com.example.Makeup.entity.Order;
import com.example.Makeup.entity.OrderItem;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.enums.OrderItemStatus;
import com.example.Makeup.mapper.OrderItemMapper;
import com.example.Makeup.repository.CartItemRepository;
import com.example.Makeup.repository.OrderItemRepository;
import com.example.Makeup.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public ApiResponse<String> createOrderItem(UUID cartId, UUID orderId){
        List<CartItem> cartItemList = cartItemRepository.findAllByCartId(cartId);
        if (cartItemList.isEmpty()){
            throw new AppException(ErrorCode.CART_IS_EMPTY);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new AppException(ErrorCode.ORDER_NOT_FOUND));

        for (CartItem cartItem : cartItemList){
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setRentalDate(cartItem.getRentalDate());
            orderItem.setStatus(OrderItemStatus.PENDING);
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteAll(cartItemList);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Order item created successfully")
                .result("Order item created successfully")
                .build();
    }

    public ApiResponse<List<OrderItemDTO>> getOrderDetail(UUID orderId){
            List<OrderItem> orderItems =   orderItemRepository.findAllByOrderId(orderId);
            if (orderItems.isEmpty()){
                throw new AppException(ErrorCode.ORDER_IS_EMPTY);
            }
            return ApiResponse.<List<OrderItemDTO>>builder()
                    .code(200)
                    .message("Order item found")
                    .result(orderItems.stream().map(orderItemMapper::toOrderItemDTO).collect(Collectors.toList()))
                    .build();
    }
}
