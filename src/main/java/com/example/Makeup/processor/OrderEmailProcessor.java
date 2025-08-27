package com.example.Makeup.processor;

import com.example.Makeup.dto.response.OrderEmailMessage;
import com.example.Makeup.entity.Order;
import com.example.Makeup.entity.OrderItem;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.repository.OrderItemRepository;
import com.example.Makeup.repository.OrderRepository;
import com.example.Makeup.service.common.EmailService;
import jakarta.mail.MessagingException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEmailProcessor {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final EmailService emailService;

  public void process(OrderEmailMessage message) throws MessagingException {
    Order order =
        orderRepository
            .findWithOrderItemsById(UUID.fromString(message.getOrderId()))
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

    List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
    order.setOrderItems(orderItems);

    emailService.sendEmail(message.getTo(), order, "Xác nhận đơn hàng #" + order.getId());
  }
}
