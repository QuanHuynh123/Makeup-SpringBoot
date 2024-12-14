package com.example.Makeup.service;

import com.example.Makeup.entity.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("$(spring.mail.username)")
    private String fromEmailId;

    public void sendEmail(String recipient, Order order, String subject) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom(fromEmailId);
        helper.setTo(recipient);
        helper.setSubject(subject);

        String body = "<p style='color:blue;'>Chào bạn, đây là nội dung đặt hàng  </p>"
                + "<p> Số lượng : " + order.getTotalQuantity() + "</p>"
                + "<p> Giá trị : " + order.getTotalPrice() + "</p>"
                + "<p> Ngày đặt : " + order.getOrderDate() + "</p>"
                + "<p> Chúng tôi sẽ chuẩn bị hàng và sớm thông báo ngày nhận ! </p> "
                + "<p style='color:red;'>Xin cảm ơn!</p>";


        // Sử dụng nội dung HTML trong body
        helper.setText(body, true); // `true` để kích hoạt HTML

        javaMailSender.send(mimeMessage);
    }
}
