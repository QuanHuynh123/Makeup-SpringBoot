package com.example.Makeup.schedule;

import com.example.Makeup.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClearOrderScheduler {

  private final IOrderService orderService;

  @Scheduled(cron = "0 0 0 */3 * ?")
  public void clearOrder() {
    orderService.clearOrderPaymentExpired();
  }
}
