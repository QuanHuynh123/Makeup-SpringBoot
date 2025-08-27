package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.request.PayRequest;
import com.example.Makeup.service.common.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/order/")
public class PayRestController {

  private final VNPAYService vnPayService;

  @PostMapping("/submit-order")
  @ResponseBody
  public String submitOrder(@RequestBody PayRequest payRequest, HttpServletRequest request) {

    String orderInfo = payRequest.getOrderInfo() + " - " + payRequest.getOrderId();
    String baseUrl =
        request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    String vnpayUrl = vnPayService.createOrder(payRequest.getAmount(), orderInfo, baseUrl, request);
    // System.out.println("VNPAY URL: " + vnpayUrl);
    return vnpayUrl;
  }
}
