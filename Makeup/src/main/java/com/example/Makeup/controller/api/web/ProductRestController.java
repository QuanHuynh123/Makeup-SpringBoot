package com.example.Makeup.controller.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductRestController {

  //    private final IProductService productService;
  //
  //    @PostMapping("/create")
  //    public ApiResponse<ProductDTO> createProduct(@ModelAttribute CreateProductRequest
  // createProduct) throws IOException{
  //        return productService.createProduct(createProduct);
  //    }
  //
  //    @DeleteMapping("/delete/{id}")
  //    public ApiResponse<String> deleteProduct(@PathVariable("id") UUID productId) {
  //        return productService.deleteProduct(productId);
  //    }
  //
  //    @PutMapping("/edit/{id}")
  //    public ApiResponse<ProductDTO> updateProduct(@ModelAttribute UpdateProductRequest
  // updateProduct, @PathVariable("id") UUID productId) throws IOException{
  //       return productService.updateProduct(updateProduct, productId);
  //    }
}
