package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.request.CreateProductRequest;
import com.example.Makeup.dto.request.UpdateProductRequest;
import com.example.Makeup.dto.response.ShortProductListResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.Product;
import com.example.Makeup.entity.SubCategory;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.mapper.ProductMapper;
import com.example.Makeup.repository.ProductRepository;
import com.example.Makeup.repository.SubCategoryRepository;
import com.example.Makeup.service.IProductService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {

  private static final String HOT_PRODUCTS_CACHE_KEY = "products-hot";
  private static final String NEW_PRODUCTS_CACHE_KEY = "products-new";
  private static final String CUSTOMER_SHOW_CACHE_KEY = "products-customer-show";
  private static final int QUANTITY_NEW_PRODUCTS = 10;
  private static final int QUANTITY_HOT_PRODUCTS = 15;

  private final ProductRepository productRepository;
  private final SubCategoryRepository subCategoryRepository;
  private final ProductMapper productMapper;
  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public ApiResponse<ProductDTO> findProductById(UUID productId) {
    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    return ApiResponse.success("Get product success", productMapper.toProductDTO(product));
  }

  @Override
  public ApiResponse<Page<ProductDTO>> getAllProducts(Pageable pageable) {
    Page<Product> products = productRepository.findAll(pageable);
    if (products.isEmpty()) {
      throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
    }
    return ApiResponse.success(
        "Get all products success", products.map(productMapper::toProductDTO));
  }

  @Override
  public ApiResponse<List<ShortProductListResponse>> getHotProducts() {
    List<Product> products =
        productRepository.findTopRentalCount(PageRequest.of(0, QUANTITY_HOT_PRODUCTS));
    List<ShortProductListResponse> dtos =
        products.stream()
            .map(
                product ->
                    new ShortProductListResponse(
                        product.getId(),
                        product.getNameProduct(),
                        product.getPrice(),
                        product.getImage().split(",")[0]))
            .collect(Collectors.toList());

    try {
      redisTemplate.opsForValue().set(HOT_PRODUCTS_CACHE_KEY, dtos, Duration.ofMinutes(30));
    } catch (Exception e) {
      log.warn("⚠️ Redis SET failed (hot products): " + e.getMessage());
    }

    return ApiResponse.success("Hot products (from DB)", dtos);
  }

  @Override
  public ApiResponse<List<ShortProductListResponse>> getNewProducts() {
    List<Product> products =
        productRepository.findNewProducts(PageRequest.of(0, QUANTITY_NEW_PRODUCTS));
    List<ShortProductListResponse> dtos =
        products.stream()
            .map(
                product ->
                    new ShortProductListResponse(
                        product.getId(),
                        product.getNameProduct(),
                        product.getPrice(),
                        product.getImage().split(",")[0]))
            .collect(Collectors.toList());

    try {
      redisTemplate.opsForValue().set(NEW_PRODUCTS_CACHE_KEY, dtos, Duration.ofMinutes(30));
    } catch (Exception e) {
      log.warn("⚠️ Redis SET failed (new products): " + e.getMessage());
    }

    return ApiResponse.success("New products (from DB)", dtos);
  }

  @Override
  public ApiResponse<List<ShortProductListResponse>> getCustomerShowProducts() {
    List<Product> products = productRepository.findRandomProducts(PageRequest.of(0, 10));
    List<ShortProductListResponse> dtos =
        products.stream()
            .map(
                product ->
                    new ShortProductListResponse(
                        product.getId(),
                        product.getNameProduct(),
                        product.getPrice(),
                        product.getImage().split(",")[0]))
            .collect(Collectors.toList());

    try {
      redisTemplate.opsForValue().set(CUSTOMER_SHOW_CACHE_KEY, dtos, Duration.ofMinutes(30));
    } catch (Exception e) {
      log.warn("⚠️ Redis SET failed (customer show products): " + e.getMessage());
    }

    return ApiResponse.success("Customer show products (from DB)", dtos);
  }

  @Override
  @Transactional
  public ApiResponse<ProductDTO> createProduct(CreateProductRequest createProduct)
      throws IOException {
    SubCategory subCategory =
        subCategoryRepository
            .findById(createProduct.getSubCategoryId())
            .orElseThrow(() -> new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND));

    Product product = new Product();
    product.setNameProduct(createProduct.getName());
    product.setDescription(createProduct.getDescription());
    product.setSize(createProduct.getSize());
    product.setPrice(createProduct.getPrice());
    product.setSubCategory(subCategory);
    product.setRentalCount(0);
    product.setCurrentQuantity(createProduct.getQuantity());

    List<String> imagePaths = new ArrayList<>();
    for (MultipartFile file : createProduct.getFiles()) {
      String path = saveImage(file);
      imagePaths.add(path);
    }
    product.setImage(String.join(",", imagePaths));

    return ApiResponse.success(
        "Product created successfully",
        productMapper.toProductDTO(productRepository.save(product)));
  }

  @Override
  @Transactional
  public ApiResponse<String> deleteProduct(UUID productId) {
    if (productRepository.existsById(productId)) {
      productRepository.deleteById(productId);
      return ApiResponse.success(
          "Product deleted successfully",
          "Product with ID " + productId + " deleted successfully.");
    } else {
      throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
    }
  }

  @Override
  public ApiResponse<Page<ProductDTO>> searchProduct(
      Integer subCategoryId, String search, Pageable pageable) {
    Page<Product> productPage = productRepository.searchProducts(subCategoryId, search, pageable);
    if (productPage.isEmpty()) {
      throw new AppException(ErrorCode.PRODUCT_IS_EMPTY);
    }

    return ApiResponse.success("Product found", productPage.map(productMapper::toProductDTO));
  }

  @Override
  public ApiResponse<List<ProductDTO>> getRelatedProducts(
      Integer subCategoryId, UUID excludedId, Pageable pageable) {
    Page<Product> page =
        productRepository.findBySubCategoryIdAndIdNot(subCategoryId, excludedId, pageable);
    List<ProductDTO> result =
        page.getContent().stream()
            .map(productMapper::toProductDTO)
            .peek(dto -> dto.setImage(dto.getImage().split(",")[0]))
            .collect(Collectors.toList());

    return ApiResponse.success("Get related products", result);
  }

  @Override
  @Transactional
  public ApiResponse<ProductDTO> updateProduct(UpdateProductRequest updateProduct, UUID productId)
      throws IOException {
    SubCategory subCategory =
        subCategoryRepository
            .findById(updateProduct.getSubCategoryId())
            .orElseThrow(() -> new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND));

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

    product.setNameProduct(updateProduct.getNameProduct());
    product.setDescription(updateProduct.getDescription());
    product.setPrice(updateProduct.getPrice());
    product.setStatus(updateProduct.isStatus());
    product.setSize(updateProduct.getSize());
    product.setSubCategory(subCategory);
    String[] images = product.getImage().split(",");

    if (updateProduct.getImage() != null) {
      for (String image : images) {
        deleteImage(image);
      }

      List<String> imagePaths = new ArrayList<>();
      for (MultipartFile file : updateProduct.getFiles()) {
        String path = saveImage(file);
        imagePaths.add(path);
      }
      product.setImage(String.join(",", imagePaths));
    }

    return ApiResponse.success(
        "Product updated successfully",
        productMapper.toProductDTO(productRepository.save(product)));
  }

  private String saveImage(MultipartFile file) throws IOException {
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images/product";
    file.transferTo(new java.io.File(uploadDir + "/" + fileName));
    return "/images/product/" + fileName;
  }

  private void deleteImage(String imagePath) {
    String filePath = Paths.get("src/main/resources/static", imagePath).toString();

    File file = new File(filePath);

    if (!file.exists()) return;

    boolean deleted = file.delete();
    if (deleted) {
      System.out.println("File deleted successfully: " + filePath);
    } else {
      System.out.println("Failed to delete file: " + filePath);
    }
  }
}
