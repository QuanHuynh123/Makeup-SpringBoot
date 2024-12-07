package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.CartItemDTO;
import com.example.Makeup.entity.OrderItem;
import com.example.Makeup.service.OrderItemService;
import com.example.Makeup.service.OrderService;
import com.example.Makeup.service.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
public class VNPAYController {
    @Autowired
    private VNPAYService vnPayService;
    private int orderTotal;
    private String orderInfo;
    private HttpServletRequest request;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;


    @GetMapping("/createOrder")
    public String home() {
        return "createOrder";
    }

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/submitOrder")
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
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, this.orderInfo, baseUrl);
        return  vnpayUrl;
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
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
            List<CartItemDTO> cartItemDTOS = (List<CartItemDTO>) model.getAttribute("cartItems");
            cutOrderInfo(orderInfo, cartItemDTOS);
            return "orderSuccess";
        }else
            return "orderfail";
    }

    public String cutOrderInfo(String orderInfo, List<CartItemDTO> cartItemDTOS) {
        System.out.println(orderInfo);
        String phoneNumber = "", email = "", name = "", message = "";
        int quantity = 0;

        // Kiểm tra nếu chuỗi không trống
        if (orderInfo != null && orderInfo.length() > 0) {
            // Cắt chuỗi, tách các tham số bằng dấu ','
            String[] parts = orderInfo.split(",");

            // Kiểm tra nếu đủ số lượng tham số
            if (parts.length >= 5) {
                phoneNumber = parts[1].trim();   // Lấy số điện thoại
                email = parts[2].trim();         // Lấy email
                name = parts[3].trim();          // Lấy tên
                message = parts[4].trim();       // Lấy thông điệp
                quantity = Integer.parseInt(parts[5].trim()); // Lấy số lượng (ép kiểu sang int)
            }
        }

        System.out.println(phoneNumber + ", " + email + ", " + name + ", " + message + ", " + quantity);
        return " " ;
    }


}
