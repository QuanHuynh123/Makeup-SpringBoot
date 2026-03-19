package com.example.Makeup.service.common;

import com.example.Makeup.config.RabbitMQConfig;
import com.example.Makeup.dto.response.OrderEmailMessage;
import com.example.Makeup.processor.OrderEmailProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEmailPublisher {

  private final RabbitTemplate rabbitTemplate;
  private final OrderEmailProcessor emailProcessor;

  /**
   * Tries to queue the email via RabbitMQ.
   * If RabbitMQ is unavailable, falls back to sending the email directly.
   */
  public void publish(OrderEmailMessage message) {
    try {
      rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, message);
      log.info("Email queued via RabbitMQ for order: {}", message.getOrderId());
    } catch (AmqpException e) {
      log.warn("RabbitMQ unavailable, sending email directly for order: {}", message.getOrderId());
      try {
        emailProcessor.process(message);
      } catch (Exception ex) {
        log.error("Failed to send email directly for order: {}", message.getOrderId(), ex);
      }
    }
  }
}
