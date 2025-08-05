package com.example.Makeup.listener;

import com.example.Makeup.dto.model.OrderItemDTO;
import com.example.Makeup.dto.response.OrderEmailMessage;
import com.example.Makeup.entity.Order;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.mapper.OrderItemMapper;
import com.example.Makeup.processor.OrderEmailProcessor;
import com.example.Makeup.repository.OrderRepository;
import com.example.Makeup.service.common.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEmailListener {

    private final OrderEmailProcessor orderEmailProcessor;

    @RabbitListener(queues = "order_queue")
    public void handleOrderEmail(OrderEmailMessage message) {
        try {
            log.info("Received message to send email for order ID: {}", message.getOrderId());
            orderEmailProcessor.process(message);
        } catch (Exception e) {
            log.error("Fail to process order email message: ", e);
        }
    }
}
