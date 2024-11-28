/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Makeup.controller.web.admin;

import com.example.Makeup.dto.ProductDTO;
import com.example.Makeup.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
public class ProductController {
    @Autowired
    ProductService productService;
    
    @GetMapping("/create")
    public String createProductPage(){
        return "create-product";
    }
    
    @GetMapping
    public String getProducts(Model model){
        List<ProductDTO> products = productService.getProducts();
        model.addAttribute("products",products);
        return "products";
    }

    
    @GetMapping("/edit/{id}")
    public String editProductPage(Model model, @PathVariable("id") int id){
        ProductDTO productDTO = productService.findById(id);
        model.addAttribute("product", productDTO);
        return "edit-product";
    }
}
