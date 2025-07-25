package com.example.Makeup.service.common;

import com.example.Makeup.entity.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmailId;

    public void sendEmail(String recipient, Order order, String subject) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom(fromEmailId);
        helper.setTo(recipient);
        helper.setSubject(subject);

        // Create a context for Thymeleaf template
        Context context = new Context();
        context.setVariable("order", order);

        String htmlBody = templateEngine.process("email/confirmation", context);

        helper.setText(htmlBody, true);

        javaMailSender.send(mimeMessage);
    }
}