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
import com.example.Makeup.service.IProductService;
import com.example.Makeup.service.ISubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final IProductService productService;
    private final ISubCategoryService subCategoryService;

    @GetMapping("/create")
    public String createProductAdminPage(Model model){
        ApiResponse<List<SubCategoryDTO>> subCategories = subCategoryService.getAll();
        model.addAttribute("subCategories", subCategories.getResult());

        return "admin/create-product";
    }

    @GetMapping("/products")
    public String getAllProductsAdminPage(Model model){
        ApiResponse<List<ProductDTO>> products = productService.getAllProducts();
        model.addAttribute("products", products.getResult());
        return "admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductAdminPage(Model model, @PathVariable("id") UUID id){
        ApiResponse<List<SubCategoryDTO>> subCategories = subCategoryService.getAll();
        model.addAttribute("subCategories", subCategories.getResult());
        
        ApiResponse<ProductDTO> productDTO = productService.findProductById(id);
        
        String[] imageList = productDTO.getResult().getImage().split(",");
        model.addAttribute("product", productDTO.getResult());
        model.addAttribute("images", imageList);
        return "admin/edit-product";
    }
}