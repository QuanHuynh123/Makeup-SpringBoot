package com.example.Makeup.controller.web.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CosplayController {
    @GetMapping("/cosplay")
    public String home(){
        return "cosplay";
    }
}
