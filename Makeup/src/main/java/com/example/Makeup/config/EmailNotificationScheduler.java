package com.example.Makeup.config;

import com.example.Makeup.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationScheduler {

    @Autowired
    AppointmentService appointmentService;

    @Scheduled(fixedRate = 5 * 60 * 1000)  // Mỗi 5 phút
    public void sendEmailNotifications() {
        System.out.println("Đang gửi thông báo email...");
        // Thực hiện công việc gửi email
    }
}
