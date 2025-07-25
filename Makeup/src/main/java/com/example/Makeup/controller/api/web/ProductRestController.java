package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.request.CreateProductRequest;
import com.example.Makeup.dto.request.UpdateProductRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IProductService;

import java.io.IOException;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductRestController {
    @Autowired
    private final IProductService productService;

    @PostMapping("/create")
    public ApiResponse<ProductDTO> createProduct(@ModelAttribute CreateProductRequest createProduct) throws IOException{
        return productService.createProduct(createProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable("id") UUID productId) {
        return productService.deleteProduct(productId);
    }

    @PutMapping("/edit/{id}")
    public ApiResponse<ProductDTO> updateProduct(@ModelAttribute UpdateProductRequest updateProduct, @PathVariable("id") UUID productId) throws IOException{
       return productService.updateProduct(updateProduct, productId);
    }
}