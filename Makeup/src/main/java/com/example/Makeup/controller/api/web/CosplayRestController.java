package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CosplayRestController {

    private final IProductService productService;

    @GetMapping("")
    public ApiResponse<Map<String, Object>> getCategoryProducts(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value="searchKey", required = false) String search) {

            Pageable pageable = PageRequest.of(page, size);
            Page<ProductDTO> productsPage = productService.searchProduct(id,search, pageable).getResult();

            Map<String, Object> response = new HashMap<>();
            response.put("products", productsPage.getContent());
            response.put("currentPage", productsPage.getNumber());
            response.put("totalPages", productsPage.getTotalPages());
            response.put("totalItems", productsPage.getTotalElements());
            return ApiResponse.success("Products retrieved successfully", response);
    }
}


