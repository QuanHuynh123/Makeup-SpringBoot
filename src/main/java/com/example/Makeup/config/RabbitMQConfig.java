package com.example.Makeup.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RabbitMQConfig {

  public static final String QUEUE = "order_queue";
  public static final String EXCHANGE = "order_exchange";
  public static final String ROUTING_KEY = "order.email";

  @Bean
  public Queue queue() {
    return new Queue(QUEUE, true); // Durable queue
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(EXCHANGE, true, false); // Durable exchange
  }

  @Bean
  public Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(
        new Jackson2JsonMessageConverter()); // Use Jackson for JSON conversion
    return template;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
      ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setRecoveryInterval(180000L); // 3 minutes

    factory.setMessageConverter(new Jackson2JsonMessageConverter());

    // Cấu hình retry
    RetryTemplate retryTemplate = new RetryTemplate();
    FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
    backOffPolicy.setBackOffPeriod(30000L); // wait 30 seconds before retrying
    retryTemplate.setBackOffPolicy(backOffPolicy);
    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
    retryPolicy.setMaxAttempts(3); // Retry up to 3 times
    retryTemplate.setRetryPolicy(retryPolicy);
    factory.setRetryTemplate(retryTemplate);

    return factory;
  }
}
