package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IProductService;
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cosplay Category API", description = "API for retrieving products by category")
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CosplayRestController {

  private final IProductService productService;

  @Operation(summary = "Get products by category with pagination and search", description = "Retrieve products filtered by category ID, with pagination and optional search keyword")
  @GetMapping("")
  public ApiResponse<Map<String, Object>> getCategoryProducts(
      @RequestParam(value = "id", required = false) Integer id,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "searchKey", required = false) String search) {

    Pageable pageable = PageRequest.of(page, size);
    Page<ProductDTO> productsPage = productService.searchProduct(id, search, pageable);

    Map<String, Object> response = new HashMap<>();
    response.put("products", productsPage.getContent());
    response.put("currentPage", productsPage.getNumber());
    response.put("totalPages", productsPage.getTotalPages());
    response.put("totalItems", productsPage.getTotalElements());
    return ApiResponse.success("Products retrieved successfully", response);
  }
}
