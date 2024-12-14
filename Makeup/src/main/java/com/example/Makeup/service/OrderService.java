package com.example.Makeup.service;

import com.example.Makeup.dto.OrderDTO;
import com.example.Makeup.entity.*;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.OrderMapper;
import com.example.Makeup.repository.*;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SendEmailService sendEmailService;
    public int createOrder(String email, String firstName, String phoneNumber, String message, int paymentMethod, int quantity, double totalPrice) {
        Order order = new Order();
        Payment payment = paymentRepository.findById(paymentMethod).orElseThrow(()-> new AppException(ErrorCode.CANT_FOUND));
        order.setPayment(payment);
        User user = userRepository.findByPhoneAndAccountNotNull(phoneNumber);
        order.setUser(user);
        order.setStatus(false);

        LocalDate localDate = LocalDate.now();
        Date sqlDate = Date.valueOf(localDate);
        order.setOrderDate(sqlDate);

        order.setTotalQuantity(quantity);
        order.setTotalPrice(totalPrice);

        Order order1 =   orderRepository.save(order);
        String subject = "Xác nhận đặt hàng";
        try {
            sendEmailService.sendEmail(user.getEmail(), order, subject);
        } catch (Exception e) {
            System.out.println("Send email fail!");
            e.printStackTrace();  // In chi tiết lỗi để bạn có thể kiểm tra
        }

        return order1.getId();
    }

    @Transactional
    public boolean checkOrder(int orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new AppException(ErrorCode.ORDER_NOT_FOUND));
        order.setStatus(true);

        LocalDate localDate = LocalDate.now(); // Lấy ngày hiện tại
        LocalDate nextDay = localDate.plusDays(1); // Tăng thêm 1 ngày
        Date sqlDate = Date.valueOf(nextDay); // Chuyển sang java.sql.Date
        order.setPickupDate(sqlDate); // Đặt giá trị pickupDate
        orderRepository.save(order);

        List<OrderItem> orderItems = orderItemRepository.findAllById(Collections.singleton(order.getId()));
        for (OrderItem orderItem : orderItems){
            Product product = productRepository.findById(orderItem.getProduct().getId()).orElseThrow();
            product.setCurrentQuantity(product.getCurrentQuantity() - orderItem.getQuantity());
            productRepository.save(product);
        }

        return true;
    }

    public List<OrderDTO> getAllApproveOrder(){
        List<Order> orders = orderRepository.findByStatus(false);
        return orders.stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrder(){
        List<Order> orders = orderRepository.findByStatus(true);
        return orders.stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }
}
