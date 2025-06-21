package com.example.Makeup.controller.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class AppointmentController {

    @RequestMapping("/appointment")
    public String appointment() {
        return "user/appointment";
    }
}
