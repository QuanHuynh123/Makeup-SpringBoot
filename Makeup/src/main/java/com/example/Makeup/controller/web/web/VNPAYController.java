package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.request.PayRequest;
import com.example.Makeup.service.common.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
public class VNPAYController {
    private final VNPAYService vnPayService;

    @GetMapping("/create-order")
    public String home() {
        return "createOrder";
    }

    @GetMapping("/vnpay-payment-return")
    public String paymentSuccessHandle(HttpServletRequest request, Model model ) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "orderSuccess" : "orderFail";
    }

//  9704198526191432198
//  NGUYEN VAN A


}
