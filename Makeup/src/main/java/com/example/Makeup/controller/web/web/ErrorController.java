package com.example.Makeup.controller.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/403")
    public String accessDenied() {
        return "error/403";
    }

    @GetMapping("/404")
    public String notExistPage() {
        return "error/404";
    }
}