package com.example.Makeup.controller.web.web;

import com.example.Makeup.service.IOrderService;
import com.example.Makeup.service.common.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class VNPAYController {
  private static final Pattern UUID_PATTERN =
      Pattern.compile("([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})");

  private final VNPAYService vnPayService;
  private final IOrderService orderService;

  @GetMapping("/create-order")
  public String home() {
    return "createOrder";
  }

  @GetMapping("/vnpay-payment-return")
  public String paymentSuccessHandle(HttpServletRequest request, Model model) {
    VNPAYService.VnpayReturnResult result = vnPayService.parseAndVerifyReturn(request);

    UUID orderId = extractOrderId(result.orderInfo());
    boolean finalized = false;
    if (result.paymentSuccess() && orderId != null) {
      finalized =
          orderService.finalizePaymentFromVnpay(
              orderId, result.transactionId(), result.txnRef(), result.amount());
    }

    int paymentStatus = result.paymentSuccess() && finalized ? 1 : 0;

    model.addAttribute("orderId", orderId != null ? orderId.toString() : result.orderInfo());
    model.addAttribute("totalPrice", result.amount());
    model.addAttribute("paymentTime", result.payDate());
    model.addAttribute("transactionId", result.transactionId());

    return paymentStatus == 1 ? "orderSuccess" : "orderFail";
  }

  private UUID extractOrderId(String orderInfo) {
    if (orderInfo == null || orderInfo.isBlank()) {
      return null;
    }
    Matcher matcher = UUID_PATTERN.matcher(orderInfo);
    UUID found = null;
    while (matcher.find()) {
      found = UUID.fromString(matcher.group(1));
    }
    return found;
  }

  //  9704198526191432198
  //  NGUYEN VAN A

}
