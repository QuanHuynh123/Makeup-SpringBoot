package com.example.Makeup.service;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.request.CreateProductRequest;
import com.example.Makeup.dto.request.UpdateProductRequest;
import com.example.Makeup.dto.response.ShortProductListResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {

  ProductDTO findProductById(UUID productId);

  Page<ProductDTO> getAllProducts(Pageable pageable);

  List<ShortProductListResponse> getHotProducts();

  List<ShortProductListResponse> getNewProducts();

  List<ShortProductListResponse> getCustomerShowProducts();

  ProductDTO createProduct(CreateProductRequest createProduct) throws IOException;

  String deleteProduct(UUID productId);

  Page<ProductDTO> searchProduct(
      Integer subCategoryId, String search, Pageable pageable);

  ProductDTO updateProduct(UpdateProductRequest updateProduct, UUID productId)
      throws IOException;

  List<ProductDTO> getRelatedProducts(
      Integer subCategoryId, UUID excludedId, Pageable pageable);
}
