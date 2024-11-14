package com.example.Makeup.controller.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/test")
    public String loginPage(){
        return "admin";
    }

    @GetMapping("/test2")
    public String loginPage2(){
        return "schedule";
    }
}
