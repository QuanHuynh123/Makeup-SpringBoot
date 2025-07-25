package com.example.Makeup.service.impl;

import com.example.Makeup.config.RabbitMQConfig;
import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.request.OrderRequest;
import com.example.Makeup.dto.response.OrderEmailMessage;
import com.example.Makeup.dto.response.OrderItemDetailResponse;
import com.example.Makeup.dto.response.OrdersAdminResponse;
import com.example.Makeup.entity.*;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.enums.ShippingType;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.enums.OrderStatus;
import com.example.Makeup.mapper.OrderMapper;
import com.example.Makeup.mapper.UserMapper;
import com.example.Makeup.repository.*;
import com.example.Makeup.service.IOrderService;
import com.example.Makeup.service.common.EmailService;
import com.example.Makeup.utils.SecurityUserUtil;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class OrderServiceImpl implements IOrderService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final UserMapper userMapper;
    private final RabbitTemplate rabbitTemplate;
    private final OrderItemServiceImpl orderItemServiceImpl;

    @Override
    @Transactional
    public ApiResponse<OrderDTO> createOrder(OrderRequest orderRequest) {
        Payment payment = paymentRepository.findById(orderRequest.getPaymentMethod()).orElseThrow(()-> new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND));
        UserDTO userDTO = SecurityUserUtil.getCurrentUser();
        User user = userMapper.toUserEntity(userDTO);

        LocalDateTime localDate = LocalDateTime.now();

        ShippingType shippingType = ShippingType.fromId(orderRequest.getTypeShipping());


        Order order = new Order();
        order.setPayment(payment);
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(localDate);
        order.setTotalQuantity(orderRequest.getQuantity());
        order.setTotalPrice(orderRequest.getTotalPrice());

        order.setReceiverEmail(orderRequest.getEmail());
        order.setReceiverName(orderRequest.getFirstName());
        order.setReceiverPhone(orderRequest.getPhoneNumber());
        order.setReceiverMessage(orderRequest.getMessage());
        order.setReceiverAddress(orderRequest.getAddress());
        order.setTypeShipping(shippingType);

        order.setReturnDate(null);

        orderRepository.saveAndFlush(order);

        orderItemServiceImpl.createOrderItem(order.getId(), orderRequest.getOrderItems());

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY,
                new OrderEmailMessage(user.getEmail(), order.getId().toString(), order.getReceiverName()));

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
    public ApiResponse<Page<OrdersAdminResponse>> getAllOrder(Pageable pageable, OrderStatus status) {
        Page<OrdersAdminResponse> orders = orderRepository.findAllOrders(status, pageable);
        if (orders.isEmpty()) {
            throw new AppException(ErrorCode.ORDER_IS_EMPTY);
        }
        return ApiResponse.success("Get all order successfully", orders);
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
    public ApiResponse<List<OrderDTO>> getMyOrders(){
            UserDTO userDTO = SecurityUserUtil.getCurrentUser();
            List<Order> orders =  orderRepository.findByUserId(userDTO.getId());
            Collections.reverse(orders);

            List<OrderDTO> orderDTOs = orders.stream()
                    .map(orderMapper::toOrderDTO)
                    .collect(Collectors.toList());
            return ApiResponse.success("Get my order success", orderDTOs);
    }

    @Override
    public ApiResponse<List<OrderItemDetailResponse>> getItemsDetail(UUID orderId) {
        List<OrderItemDetailResponse> orderItemDetailResponses = orderItemRepository.findOrderItemsDetailByOrderId(orderId);
        if (orderItemDetailResponses.isEmpty()) {
            throw new AppException(ErrorCode.ORDER_ITEM_NOT_FOUND);
        }

        List<OrderItemDetailResponse> responses = orderItemDetailResponses.stream()
                .map(orderItemDetailResponse -> new OrderItemDetailResponse(
                        orderItemDetailResponse.getId(),
                        orderItemDetailResponse.getQuantity(),
                        orderItemDetailResponse.getPrice(),
                        orderItemDetailResponse.getStatus(),
                        orderItemDetailResponse.getRentalDate(),
                        orderItemDetailResponse.getOrderId(),
                        orderItemDetailResponse.getProductId(),
                        orderItemDetailResponse.getProductName(),
                        orderItemDetailResponse.getFirstImageUrl().split(",")[0],
                        orderItemDetailResponse.getCreatedAt(),
                        orderItemDetailResponse.getUpdatedAt()
                ))
                .toList();
        return ApiResponse.success("Get order items detail successfully", responses);
    }
}
