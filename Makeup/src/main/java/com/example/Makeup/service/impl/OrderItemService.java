package com.example.Makeup.service.impl;

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
import com.example.Makeup.service.IOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService implements IOrderItemService {
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
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
        return ApiResponse.success("Order items created successfully", null);
    }

    @Override
    public ApiResponse<List<OrderItemDTO>> getOrderDetail(UUID orderId){
            List<OrderItem> orderItems =   orderItemRepository.findAllByOrderId(orderId);
            if (orderItems.isEmpty()){
                throw new AppException(ErrorCode.ORDER_IS_EMPTY);
            }

            List<OrderItemDTO> orderItemDTOs = orderItems.stream()
                    .map(orderItemMapper::toOrderItemDTO)
                    .collect(Collectors.toList());
            return ApiResponse.success("Order items retrieved successfully", orderItemDTOs);
    }
}
