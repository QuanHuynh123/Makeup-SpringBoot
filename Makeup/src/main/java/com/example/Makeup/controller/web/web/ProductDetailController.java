package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ProductDetailController {

    @Autowired
    ProductService productService;

    @GetMapping("/productDetail/{id}")
    public String home(Model model , @PathVariable("id") UUID idProduct){
        ProductDTO product = productService.findProductById(idProduct).getResult();
        model.addAttribute("product",product);
        List<String> images = Arrays.stream(product.getImage().split(","))
                .map(String::trim)  // Trim whitespace from each image URL
                .collect(Collectors.toList());
        model.addAttribute("images", images);
        return "user/productDetail";
    }
}
