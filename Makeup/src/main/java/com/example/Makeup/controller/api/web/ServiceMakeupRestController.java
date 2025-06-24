package com.example.Makeup.controller.api.web;

import com.example.Makeup.service.ServiceMakeupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/serviceMakeups")
public class ServiceMakeupRestController {


    private final ServiceMakeupService serviceMakeupService;

    public ServiceMakeupRestController(ServiceMakeupService serviceMakeupService) {
        this.serviceMakeupService = serviceMakeupService;
    }

}
