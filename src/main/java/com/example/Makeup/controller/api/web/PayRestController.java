package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.request.PayRequest;
import com.example.Makeup.service.common.VNPAYService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment API", description = "API for payment operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/order/")
public class PayRestController {

  private final VNPAYService vnPayService;

  @Operation(summary = "Submit order for payment", description = "Submit an order and get the VNPAY payment URL")
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
