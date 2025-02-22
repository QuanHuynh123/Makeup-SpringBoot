package com.example.Makeup.controller.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MakeupController {

    @GetMapping("/makeup")
    public String home(){
        return "user/makeup";
    }
}
