package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.request.CreateProductRequest;
import com.example.Makeup.dto.request.UpdateProductRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IProductService;
import java.io.IOException;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product Admin API", description = "API for admin product operations")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/products")
public class ProductAdminRestController {

  private final IProductService productService;

  @Operation(summary = "Get all products", description = "Retrieve a paginated list of all products")
  @GetMapping
  public ApiResponse<Page<ProductDTO>> getAllProducts(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "sort", defaultValue = "createdAt,desc") String sort) {

    Pageable pageable =
        PageRequest.of(
            page, size, Sort.by(Sort.Direction.fromString(sort.split(",")[1]), sort.split(",")[0]));
    return ApiResponse.success("Get all products success",productService.getAllProducts(pageable));
  }

  @Operation(summary = "Create a new product", description = "Create a new product with the provided details")
  @PostMapping()
  public ApiResponse<ProductDTO> createProduct(@ModelAttribute CreateProductRequest createProduct)
      throws IOException {
    return ApiResponse.success("Product created successfully",productService.createProduct(createProduct));
  }

  @Operation(summary = "Update a product", description = "Update an existing product by product ID")
  @PatchMapping("{id}")
  public ApiResponse<ProductDTO> updateProduct(
      UpdateProductRequest productDTO, @PathVariable("id") UUID productId) throws IOException {
    return ApiResponse.success("Product updated successfully",productService.updateProduct(productDTO, productId));
  }

  @Operation(summary = "Delete a product", description = "Delete a product by product ID")
  @DeleteMapping("{id}")
  public ApiResponse<String> deleteProduct(@PathVariable("id") UUID productId) {
    return ApiResponse.success(
            "Product deleted successfully",
            productService.deleteProduct(productId));
  }

  @Operation(summary = "Get product by ID", description = "Retrieve product details by product ID")
  @GetMapping("{id}")
  public ApiResponse<ProductDTO> findProductById(@PathVariable("id") UUID productId) {
    return ApiResponse.success("Get product success", productService.findProductById(productId));
  }
}
