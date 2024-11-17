package com.example.Makeup.controller.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductDetailController {

    @RequestMapping("/productDetail")
    public String home(){
        return "productDetail";
    }
}
