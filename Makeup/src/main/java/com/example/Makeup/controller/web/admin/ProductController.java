/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Makeup.controller.web.admin;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.model.SubCategoryDTO;
import java.util.List;
import java.util.UUID;

import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.ProductService;
import com.example.Makeup.service.SubCategoryService;
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

    @Autowired
    SubCategoryService subCategoryService;

    @GetMapping("/create")
    public String createProductPage(Model model){
        ApiResponse<List<SubCategoryDTO>> subCategories = subCategoryService.getAll();
        model.addAttribute("subCategories", subCategories.getResult());

        return "admin/create-product";
    }

    @GetMapping
    public String getAllProducts(Model model){
        ApiResponse<List<ProductDTO>> products = productService.getAllProducts();
        model.addAttribute("products", products.getResult());
        return "admin/products";
    }


    @GetMapping("/edit/{id}")
    public String editProductPage(Model model, @PathVariable("id") UUID id){
        ApiResponse<List<SubCategoryDTO>> subCategories = subCategoryService.getAll();
        model.addAttribute("subCategories", subCategories.getResult());
        
        ApiResponse<ProductDTO> productDTO = productService.findProductById(id);
        
        String[] imageList = productDTO.getResult().getImage().split(",");
        model.addAttribute("product", productDTO.getResult());
        model.addAttribute("images", imageList);
        return "admin/edit-product";
    }
}