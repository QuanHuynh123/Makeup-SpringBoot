package com.example.Makeup.controller.api.web;

import com.example.Makeup.entity.ServiceMakeup;
import com.example.Makeup.service.ServiceMakeupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookingServiceController {

    @Autowired
    private ServiceMakeupService serviceMakeupService;

    // API lấy danh sách các dịch vụ Makeup
    @GetMapping("/ServiceMakeups")
    public List<ServiceMakeup> getAllServices() {
        return serviceMakeupService.getAllServices();
    }


}

