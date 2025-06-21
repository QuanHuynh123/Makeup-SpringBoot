package com.example.Makeup.service;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.entity.*;
import com.example.Makeup.enums.ApiResponse;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.enums.OrderStatus;
import com.example.Makeup.mapper.OrderMapper;
import com.example.Makeup.repository.*;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final SendEmailService sendEmailService;

    public OrderService(PaymentRepository paymentRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserRepository userRepository, OrderMapper orderMapper, ProductRepository productRepository, SendEmailService sendEmailService) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
        this.productRepository = productRepository;
        this.sendEmailService = sendEmailService;
    }

    @Transactional
    public ApiResponse<OrderDTO> createOrder(String email, String firstName, String phoneNumber, String message, int paymentMethod, int quantity, double totalPrice) {
        Payment payment = paymentRepository.findById(paymentMethod).orElseThrow(()-> new AppException(ErrorCode.CANT_FOUND));
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

        return ApiResponse.<OrderDTO>builder()
                .code(200)
                .message("Create order successfully")
                .result(orderMapper.toOrderDTO(order))
                .build();
    }

    public ApiResponse<OrderDTO> getOrder(UUID orderId){
        Order order  = orderRepository.findById(orderId).orElseThrow(()-> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return ApiResponse.<OrderDTO>builder()
                .code(200)
                .message("Get order successfully")
                .result(orderMapper.toOrderDTO(order))
                .build();
    }

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
                throw new AppException(ErrorCode.QUANTITY_NOT_ENOUGH);
            }
            product.setCurrentQuantity(remaining);

            try {
                productRepository.save(product);
            } catch (OptimisticLockException e) {
                throw new AppException(ErrorCode.PRODUCT_CONFLICT);
            }
        }

        return ApiResponse.<Boolean>builder()
                .code(200)
                .message("Check order successfully")
                .result(true)
                .build();
    }

    public ApiResponse<List<OrderDTO>> getAllApproveOrder(){
        List<Order> orders = orderRepository.findByStatus(OrderStatus.COMPLETED);
        if (orders.isEmpty()){
            throw new AppException(ErrorCode.IS_EMPTY);
        }
        return ApiResponse.<List<OrderDTO>>builder()
                .code(200)
                .message("Get all approve order successfully")
                .result(orders.stream()
                        .map(orderMapper::toOrderDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public ApiResponse<List<OrderDTO>> getAllOrder(){
        List<Order> orders = orderRepository.findByStatus(OrderStatus.PENDING);
        if (orders.isEmpty()){
            throw new AppException(ErrorCode.IS_EMPTY);
        }
        return ApiResponse.<List<OrderDTO>>builder()
                .code(200)
                .message("Get all order successfully")
                .result(orders.stream()
                        .map(orderMapper::toOrderDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public ApiResponse<Boolean> returnProductOfOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
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

        return ApiResponse.<Boolean>builder()
                .code(200)
                .message("Return product successfully")
                .result(true)
                .build();
    }

    public ApiResponse<List<OrderDTO>> getMyOrder(UUID userId){
            List<Order> orders =  orderRepository.findByUserId(userId);
            Collections.reverse(orders);
            if (orders.isEmpty()){
                return ApiResponse.<List<OrderDTO>>builder()
                        .code(404)
                        .message("No orders found for this user")
                        .result(Collections.emptyList())
                        .build();
            }
            return ApiResponse.<List<OrderDTO>>builder()
                    .code(200)
                    .message("Get my order successfully")
                    .result(orders.stream()
                            .map(orderMapper::toOrderDTO)
                            .collect(Collectors.toList()))
                    .build();
    }
}
