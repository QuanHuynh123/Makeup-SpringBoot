package com.example.Makeup.controller.web.web;

import com.example.Makeup.service.impl.OrderItemServiceImpl;
import com.example.Makeup.service.impl.OrderServiceImpl;
import com.example.Makeup.service.impl.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class VNPAYController {
    @Autowired
    private VNPAYService vnPayService;
    private int orderTotal;
    private String orderInfo;
    private HttpServletRequest request;

    @GetMapping("/create-order")
    public String home() {
        return "createOrder";
    }

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/submit-order")
    @ResponseBody
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              @RequestParam("phoneNumber") String phoneNumber,
                              @RequestParam("email") String email,
                              @RequestParam("firstName") String firstName,
                              @RequestParam("message") String message,
                              @RequestParam("quantity") int quantity,
                              HttpServletRequest request) {
        this.orderTotal = orderTotal;
        this.orderInfo = orderInfo +
                ","+ phoneNumber +
                "," + email +
                "," + firstName +
                "," + message +
                "," + quantity;
        this.request = request;
        System.out.println(this.orderInfo);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return vnPayService.createOrder(request, orderTotal, this.orderInfo, baseUrl);
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model , HttpSession session) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if(paymentStatus ==1 ) {
            int cartId = (int) session.getAttribute("cartId");
            cutOrderInfo(orderInfo, Double.parseDouble(totalPrice),  cartId);
            return "orderSuccess";
        }else
            return "orderfail";
    }

    public String cutOrderInfo(String orderInfo, double totalPrice, int cartId) {
        return " " ;
    }

//  9704198526191432198
//  NGUYEN VAN A


}
