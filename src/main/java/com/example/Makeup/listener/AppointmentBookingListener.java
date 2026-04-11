package com.example.Makeup.listener;

import com.example.Makeup.config.RabbitMQConfig;
import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.request.AppointmentBookingMessage;
import com.example.Makeup.service.impl.AppointmentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppointmentBookingListener {

  private final AppointmentServiceImpl appointmentService;

  @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_QUEUE)
  public AppointmentDTO handleBooking(AppointmentBookingMessage message) {
    log.info("Received appointment booking message token={}", message.getBookingToken());
    return appointmentService.createAppointmentDirect(
        message.getAppointmentRequest(), message.getAccountId(), message.getBookingToken());
  }
}