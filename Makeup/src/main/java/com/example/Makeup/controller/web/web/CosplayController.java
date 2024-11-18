package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.ProductDTO;
import com.example.Makeup.entity.Product;
import com.example.Makeup.service.ProductService;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CosplayController {
    @Autowired
    ProductService productService;
    
    @GetMapping("/cosplay")
    public String home(Model model){
        List<ProductDTO> products = productService.getProducts();
        model.addAttribute("products", products);
        return "cosplay";
    }
    
    
}
