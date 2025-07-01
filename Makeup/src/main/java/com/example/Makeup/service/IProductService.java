package com.example.Makeup.service;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.request.CreateProductRequest;
import com.example.Makeup.dto.request.UpdateProductRequest;
import com.example.Makeup.dto.response.ShortProductListResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IProductService {

    ApiResponse<ProductDTO> findProductById(UUID productId);
    ApiResponse<List<ProductDTO>> getAllProducts();
    ApiResponse<List<ShortProductListResponse>> getHotProducts();
    ApiResponse<List<ShortProductListResponse>> getNewProducts();
    ApiResponse<List<ShortProductListResponse>> getCustomerShowProducts();
    ApiResponse<ProductDTO> createProduct(CreateProductRequest createProduct) throws IOException;
    ApiResponse<String> deleteProduct(UUID productId);
    ApiResponse<Page<ProductDTO>> getProductBySubcategoryId(int subCategoryId, int page, int size);
    ApiResponse<ProductDTO> updateProduct(UpdateProductRequest updateProduct, UUID productId) throws IOException;
}
