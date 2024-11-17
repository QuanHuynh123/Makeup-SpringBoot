package com.example.Makeup.controller.api.web;

import com.example.Makeup.entity.ServiceMakeup;
import com.example.Makeup.service.ServiceMakeupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/serviceMakeups")
public class ServiceMakeupRestController {

    @Autowired
    private ServiceMakeupService serviceMakeupService;

    // API lấy danh sách các dịch vụ Makeup
    @GetMapping("/services")
    public List<ServiceMakeup> getAllServices() {
        return serviceMakeupService.getAllServices();
    }
}
