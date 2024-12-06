package com.example.Makeup.controller.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyOrderController {

    @RequestMapping("/myOrder")
    public String myOrder(){
        return "myOrder";
    }
}
