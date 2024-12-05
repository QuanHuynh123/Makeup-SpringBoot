package com.example.Makeup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);  // Số lượng thread tối đa trong pool
        scheduler.setThreadNamePrefix("scheduled-task-"); // Tiền tố tên thread
        scheduler.setDaemon(true); // Đảm bảo các thread là daemon để ứng dụng không bị dừng khi còn thread đang chạy
        return scheduler;
    }
}
