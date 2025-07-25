package com.example.Makeup.listener;

import com.example.Makeup.dto.response.OrderEmailMessage;
import com.example.Makeup.entity.Order;
import com.example.Makeup.repository.OrderRepository;
import com.example.Makeup.service.common.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEmailListener {

    private final EmailService emailService;
    private final OrderRepository orderRepository;

    @RabbitListener(queues = "order_queue")
    public void handleOrderEmail(OrderEmailMessage message) {
        try {
            log.info("Received message to send email for order ID: {}", message.getOrderId());

            Order order = orderRepository.findById(UUID.fromString(message.getOrderId()))
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            emailService.sendEmail(message.getTo(), order, "Xác nhận đơn hàng #" + order.getId());
        } catch (Exception e) {
            log.error("Fail to send email: ", e);
        }
    }
}
