package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.request.CreateProductRequest;
import com.example.Makeup.dto.request.UpdateProductRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IProductService;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/products")
public class ProductAdminRestController {

  private final IProductService productService;

  @GetMapping
  public ApiResponse<Page<ProductDTO>> getAllProducts(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "sort", defaultValue = "createdAt,desc") String sort) {

    Pageable pageable =
        PageRequest.of(
            page, size, Sort.by(Sort.Direction.fromString(sort.split(",")[1]), sort.split(",")[0]));
    return productService.getAllProducts(pageable);
  }

  @PostMapping("/create")
  public ApiResponse<ProductDTO> createProduct(@ModelAttribute CreateProductRequest createProduct)
      throws IOException {
    return productService.createProduct(createProduct);
  }

  @PutMapping("{id}/update")
  public ApiResponse<ProductDTO> updateProduct(
      UpdateProductRequest productDTO, @PathVariable("id") UUID productId) throws IOException {
    return productService.updateProduct(productDTO, productId);
  }

  @DeleteMapping("{id}/delete")
  public ApiResponse<String> deleteProduct(@PathVariable("id") UUID productId) {
    return productService.deleteProduct(productId);
  }

  @GetMapping("{id}")
  public ApiResponse<ProductDTO> findProductById(@PathVariable("id") UUID productId) {
    return productService.findProductById(productId);
  }
}
