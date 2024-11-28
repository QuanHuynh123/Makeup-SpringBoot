package com.example.Makeup.controller.web.web;

import com.example.Makeup.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductDetailController {

    @Autowired
    ProductService productService;
    @RequestMapping("/productDetail/{id}")
    public String home(@PathVariable("id") int idProduct){
        return "user/productDetail";
    }
}
