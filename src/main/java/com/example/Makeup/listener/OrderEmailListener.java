package com.example.Makeup.listener;

import com.example.Makeup.config.RabbitMQConfig;
import com.example.Makeup.dto.response.OrderEmailMessage;
import com.example.Makeup.processor.OrderEmailProcessor;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEmailListener {

  private final OrderEmailProcessor orderEmailProcessor;

  @RabbitListener(queues = RabbitMQConfig.QUEUE)
  public void handleOrderEmail(OrderEmailMessage message) throws MessagingException {
    log.info("Received message to send email for order ID: {}", message.getOrderId());
    orderEmailProcessor.process(message);
  }
}
