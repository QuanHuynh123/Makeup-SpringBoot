package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.request.OrderRequest;
import com.example.Makeup.dto.response.OrderEmailMessage;
import com.example.Makeup.dto.response.OrderItemDetailResponse;
import com.example.Makeup.dto.response.OrdersAdminResponse;
import com.example.Makeup.entity.*;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.enums.OrderStatus;
import com.example.Makeup.enums.ShippingType;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.mapper.OrderMapper;
import com.example.Makeup.mapper.UserMapper;
import com.example.Makeup.repository.*;
import com.example.Makeup.service.IOrderService;
import com.example.Makeup.service.common.OrderEmailPublisher;
import com.example.Makeup.utils.SecurityUserUtil;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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
  private final OrderEmailPublisher orderEmailPublisher;
  private final OrderItemServiceImpl orderItemServiceImpl;
  private final SimpMessagingTemplate messagingTemplate;

  @Override
  @Transactional
  public OrderDTO createOrder(OrderRequest orderRequest) {
    if (orderRepository.existsByUniqueRequestId(orderRequest.getUniqueRequestId())) {
      throw new AppException(ErrorCode.DUPLICATE_ORDER);
    }

    Payment payment =
        paymentRepository
            .findById(orderRequest.getPaymentMethod())
            .orElseThrow(() -> new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND));
    UserDTO userDTO = SecurityUserUtil.getCurrentUser();
    User user = userMapper.toUserEntity(userDTO);

    LocalDateTime localDate = LocalDateTime.now();

    ShippingType shippingType = ShippingType.fromId(orderRequest.getTypeShipping());

    Order order = new Order();
    order.setPayment(payment);
    order.setUser(user);
    order.setStatus(
        orderRequest.getPaymentMethod() == 1
            ? OrderStatus.PENDING
            : OrderStatus.PAYMENT_NOT_COMPLETED);
    order.setOrderDate(localDate);
    order.setTotalQuantity(orderRequest.getQuantity());
    order.setTotalPrice(orderRequest.getTotalPrice());

    order.setReceiverEmail(orderRequest.getEmail());
    order.setReceiverName(orderRequest.getFirstName());
    order.setReceiverPhone(orderRequest.getPhoneNumber());
    order.setReceiverMessage(orderRequest.getMessage());
    order.setReceiverAddress(orderRequest.getAddress());
    order.setUniqueRequestId(orderRequest.getUniqueRequestId());
    order.setTypeShipping(shippingType);

    order.setReturnDate(null);

    try {
      orderRepository.saveAndFlush(order);
    } catch (DataIntegrityViolationException ex) {
      throw new AppException(ErrorCode.DUPLICATE_ORDER);
    }

    orderItemServiceImpl.createOrderItem(order.getId(), orderRequest.getOrderItems());

    final OrderEmailMessage emailMessage =
        new OrderEmailMessage(user.getEmail(), order.getId().toString(), order.getReceiverName());
    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
      @Override
      public void afterCommit() {
        orderEmailPublisher.publish(emailMessage);
      }
    });

    final SimpMessagingTemplate wsTemplate = messagingTemplate;
    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
      @Override
      public void afterCommit() {
        wsTemplate.convertAndSend("/topic/orders", "NEW_ORDER");
      }
    });

    return orderMapper.toOrderDTO(order);
  }

  @Override
  public OrderDTO getOrder(UUID orderId) {
    UserDTO currentUser = SecurityUserUtil.getCurrentUser();
    Order order =
      orderRepository
        .findById(orderId)
        .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    if (!order.getUser().getId().equals(currentUser.getId())) {
      throw new AppException(ErrorCode.ACCESS_DENIED);
    }
    return orderMapper.toOrderDTO(order);
  }

  @Override
  @Transactional
  public Boolean checkOrder(UUID orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

    if (order.getStatus() == OrderStatus.IN_PROGRESS) {
      return true;
    }
    if (order.getStatus() != OrderStatus.PENDING) {
      throw new AppException(ErrorCode.ORDER_CONFLICT);
    }

    // set pickup date to tomorrow
    LocalDateTime time = LocalDate.now().plusDays(1).atStartOfDay();
    int updatedOrderRows =
        orderRepository.updateOrderStatusAndPickupDateIfCurrentStatus(
            orderId, OrderStatus.PENDING, OrderStatus.IN_PROGRESS, time);
    if (updatedOrderRows == 0) {
      throw new AppException(ErrorCode.ORDER_CONFLICT);
    }

    List<OrderItem> orderItems =
        orderItemRepository.findAllByOrderId(order.getId());

    for (OrderItem orderItem : orderItems) {
      try {
        int updatedProductRows =
            productRepository.decreaseQuantityIfEnough(
                orderItem.getProduct().getId(), orderItem.getQuantity());
        if (updatedProductRows == 0) {
          if (!productRepository.existsById(orderItem.getProduct().getId())) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
          }
          throw new AppException(ErrorCode.PRODUCT_QUANTITY_NOT_ENOUGH);
        }
      } catch (ObjectOptimisticLockingFailureException e) {
        throw new AppException(ErrorCode.PRODUCT_CONFLICT);
      }
    }

    return  true;
  }

  @Override
  public Page<OrdersAdminResponse> getAllOrder(Pageable pageable, Integer statusId) {
    OrderStatus status = null;
    if (statusId != null && statusId > 0) status = OrderStatus.fromId(statusId);

    Page<OrdersAdminResponse> orders = orderRepository.findAllOrders(status, pageable);
    if (orders.isEmpty()) {
      throw new AppException(ErrorCode.ORDER_IS_EMPTY);
    }
    return  orders;
  }

  @Override
  @Transactional
  public Boolean returnProductOfOrder(UUID orderId) {
    if (!orderRepository.existsById(orderId)) {
      throw new AppException(ErrorCode.ORDER_NOT_FOUND);
    }

    int markedRows = orderRepository.markOrderReturnedIfNotYet(orderId, LocalDateTime.now());
    if (markedRows == 0) {
      return true;
    }

    List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);

    for (OrderItem orderItem : orderItems) {
      try {
        int updatedProductRows =
            productRepository.increaseQuantity(orderItem.getProduct().getId(), orderItem.getQuantity());
        if (updatedProductRows == 0) {
          throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
      } catch (ObjectOptimisticLockingFailureException e) {
        throw new AppException(ErrorCode.PRODUCT_CONFLICT);
      }
    }

    return  true;
  }

  @Override
  public List<OrderDTO> getMyOrders() {
    UserDTO userDTO = SecurityUserUtil.getCurrentUser();
    List<Order> orders = orderRepository.findByUserId(userDTO.getId());
    Collections.reverse(orders);

    return orders.stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
  }

  @Override
  public List<OrderItemDetailResponse> getItemsDetail(UUID orderId) {
    UserDTO currentUser = SecurityUserUtil.getCurrentUser();
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    if (!order.getUser().getId().equals(currentUser.getId())) {
      throw new AppException(ErrorCode.ACCESS_DENIED);
    }
    List<OrderItemDetailResponse> orderItemDetailResponses =
        orderItemRepository.findOrderItemsDetailByOrderId(orderId);
    if (orderItemDetailResponses.isEmpty()) {
      throw new AppException(ErrorCode.ORDER_ITEM_NOT_FOUND);
    }

    List<OrderItemDetailResponse> responses =
        orderItemDetailResponses.stream()
            .map(
                orderItemDetailResponse ->
                    new OrderItemDetailResponse(
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
                        orderItemDetailResponse.getUpdatedAt()))
            .toList();
    return  responses;
  }

  @Override
  @Transactional
  public String updateOrderStatus(UUID orderId, int status) {
    UserDTO currentUser = SecurityUserUtil.getCurrentUser();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

    boolean isAdminOrStaff =
        authentication != null
            && authentication.getAuthorities().stream()
                .anyMatch(
                    authority ->
                        "ROLE_ADMIN".equals(authority.getAuthority())
                            || "ROLE_STAFF".equals(authority.getAuthority()));
    if (!isAdminOrStaff && !order.getUser().getId().equals(currentUser.getId())) {
      throw new AppException(ErrorCode.ACCESS_DENIED);
    }

    OrderStatus newStatus = OrderStatus.fromId(status); // Validate status

    if (order.getStatus() == newStatus) {
      throw new AppException(ErrorCode.ORDER_ALREADY_IN_THIS_STATUS);
    }

    int updatedRows =
        orderRepository.updateOrderStatusIfCurrentStatus(orderId, order.getStatus(), newStatus);
    if (updatedRows == 0) {
      throw new AppException(ErrorCode.ORDER_CONFLICT);
    }
    return  "Order status updated";
  }

  @Override
  @Transactional
  public Boolean finalizePaymentFromVnpay(
      UUID orderId, String transactionId, String vnpTxnRef, long paidAmount) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

    if (order.getStatus() == OrderStatus.PENDING) {
      return true;
    }
    if (order.getStatus() != OrderStatus.PAYMENT_NOT_COMPLETED) {
      throw new AppException(ErrorCode.ORDER_CONFLICT);
    }
    if (paidAmount <= 0) {
      throw new AppException(ErrorCode.PAYMENT_VERIFY_FAILED);
    }

    int updatedRows =
        orderRepository.updateOrderStatusIfCurrentStatus(
            orderId, OrderStatus.PAYMENT_NOT_COMPLETED, OrderStatus.PENDING);
    if (updatedRows == 0) {
      Order reloaded =
          orderRepository
              .findById(orderId)
              .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
      if (reloaded.getStatus() == OrderStatus.PENDING) {
        return true;
      }
      throw new AppException(ErrorCode.ORDER_CONFLICT);
    }

    log.info(
        "Finalized VNPAY payment for order {} (txnRef={}, transactionId={})",
        orderId,
        vnpTxnRef,
        transactionId);
    return true;
  }

  @Override
  public Boolean checkRepaymentCondition(UUID orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    if (order.getStatus() != OrderStatus.PAYMENT_NOT_COMPLETED) {
      throw new AppException(ErrorCode.ORDER_REPAYMENT_CONDITION_NOT_MET);
    }
    if (order.getCreatedAt().isBefore(LocalDateTime.now().minusDays(7))) {
      throw new AppException(ErrorCode.ORDER_REPAYMENT_CONDITION_NOT_MET);
    }

    return true;
  }

  @Transactional
  @Override
  public void clearOrderPaymentExpired() {
    LocalDateTime expiredTime = LocalDateTime.now().minusDays(7);
    int clearedOrders = orderRepository.clearOrderPaymentExpired(expiredTime);
    if (clearedOrders > 0) {
      log.info("Cleared {} expired orders", clearedOrders);
    } else {
      log.info("No expired orders to clear");
    }
  }
}
