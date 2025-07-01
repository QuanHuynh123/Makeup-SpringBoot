package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.entity.*;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.enums.OrderStatus;
import com.example.Makeup.mapper.OrderMapper;
import com.example.Makeup.repository.*;
import com.example.Makeup.service.IOrderService;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService implements IOrderService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ApiResponse<OrderDTO> createOrder(String email, String firstName, String phoneNumber, String message, int paymentMethod, int quantity, double totalPrice) {
        Payment payment = paymentRepository.findById(paymentMethod).orElseThrow(()-> new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND));
        User user = new User(email, firstName, phoneNumber, message);
        LocalDateTime localDate = LocalDateTime.now();

        Order order = new Order();
        order.setPayment(payment);
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(localDate);
        order.setTotalQuantity(quantity);
        order.setTotalPrice(totalPrice);
        order.setReturnDate(null);

        orderRepository.save(order);

        //sendEmailService.sendEmail(user.getEmail(), order, subject);

        return ApiResponse.success("Create order successfully", orderMapper.toOrderDTO(order));
    }

    @Override
    public ApiResponse<OrderDTO> getOrder(UUID orderId){
        Order order  = orderRepository.findById(orderId).orElseThrow(()-> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return ApiResponse.success("Get order successfully", orderMapper.toOrderDTO(order));
    }

    @Override
    @Transactional
    public ApiResponse<Boolean> checkOrder(UUID orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setStatus(OrderStatus.IN_PROGRESS);

        // set pickup date to tomorrow
        LocalDateTime time = LocalDate.now().plusDays(1).atStartOfDay();
        order.setPickupDate(time);
        orderRepository.save(order);

        List<OrderItem> orderItems = orderItemRepository.findAllById(Collections.singleton(order.getId()));

        for (OrderItem orderItem : orderItems) {
            Product product = productRepository.findById(orderItem.getProduct().getId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            int remaining = product.getCurrentQuantity() - orderItem.getQuantity();
            if (remaining < 0) {
                throw new AppException(ErrorCode.PRODUCT_QUANTITY_NOT_ENOUGH);
            }
            product.setCurrentQuantity(remaining);

            try {
                productRepository.save(product);
            } catch (OptimisticLockException e) {
                throw new AppException(ErrorCode.PRODUCT_CONFLICT);
            }
        }

        return ApiResponse.success("Check order successfully", true);
    }

    @Override
    public ApiResponse<List<OrderDTO>> getAllApproveOrder(){
        List<Order> orders = orderRepository.findByStatus(OrderStatus.COMPLETED);
        if (orders.isEmpty()){
            throw new AppException(ErrorCode.ORDER_IS_EMPTY);
        }
        List<OrderDTO> orderDTOs = orders.stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
        return ApiResponse.success("Get all approve order successfully", orderDTOs);
    }

    @Override
    public ApiResponse<List<OrderDTO>> getAllOrder(){
        List<Order> orders = orderRepository.findByStatus(OrderStatus.PENDING);
        if (orders.isEmpty()){
            throw new AppException(ErrorCode.ORDER_IS_EMPTY);
        }
        List<OrderDTO> orderDTOs = orders.stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
        return ApiResponse.success("Get all order successfully", orderDTOs);
    }

    @Override
    public ApiResponse<Boolean> returnProductOfOrder(UUID orderId) {
        orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);

        for (OrderItem orderItem : orderItems) {
            Product product = productRepository.findById(orderItem.getProduct().getId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            product.setCurrentQuantity(product.getCurrentQuantity() + orderItem.getQuantity());

            try {
                productRepository.save(product);
            } catch (OptimisticLockException e) {
                throw new AppException(ErrorCode.PRODUCT_CONFLICT);
            }
        }

        return ApiResponse.success("Return product of order successfully", true);
    }

    @Override
    public ApiResponse<List<OrderDTO>> getMyOrder(UUID userId){
            List<Order> orders =  orderRepository.findByUserId(userId);
            Collections.reverse(orders);

            List<OrderDTO> orderDTOs = orders.stream()
                    .map(orderMapper::toOrderDTO)
                    .collect(Collectors.toList());
            return ApiResponse.success("Get my order success", orderDTOs);
    }
}
