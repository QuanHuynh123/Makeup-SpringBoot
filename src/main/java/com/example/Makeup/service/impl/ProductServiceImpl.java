package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.request.CreateProductRequest;
import com.example.Makeup.dto.request.UpdateProductRequest;
import com.example.Makeup.dto.response.ShortProductListResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
  private static final int QUANTITY_NEW_PRODUCTS = 10;
  private static final int QUANTITY_HOT_PRODUCTS = 15;

  private final ProductRepository productRepository;
  private final SubCategoryRepository subCategoryRepository;
  private final ProductMapper productMapper;

  @Override
  public ProductDTO findProductById(UUID productId) {
    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    return productMapper.toProductDTO(product);
  }

  @Override
  public Page<ProductDTO> getAllProducts(Pageable pageable) {
    Page<Product> products = productRepository.findAll(pageable);
    if (products.isEmpty()) {
      throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
    }
    return products.map(productMapper::toProductDTO);
  }

  @Override
  public List<ShortProductListResponse> getHotProducts() {
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

    return  dtos;
  }

  @Override
  public List<ShortProductListResponse> getNewProducts() {
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

    return  dtos;
  }

  @Override
  public List<ShortProductListResponse> getCustomerShowProducts() {
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

    return dtos;
  }

  @Override
  @Transactional
  public ProductDTO createProduct(CreateProductRequest createProduct)
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

    return productMapper.toProductDTO(productRepository.save(product));
  }

  @Override
  @Transactional
  public String deleteProduct(UUID productId) {
    if (productRepository.existsById(productId)) {
      productRepository.deleteById(productId);
      return "Product with ID " + productId + " deleted successfully.";
    } else {
      throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
    }
  }

  @Override
  public Page<ProductDTO> searchProduct(
      Integer subCategoryId, String search, Pageable pageable) {
    Page<Product> productPage = productRepository.searchProducts(subCategoryId, search, pageable);
    if (productPage.isEmpty()) {
      throw new AppException(ErrorCode.PRODUCT_IS_EMPTY);
    }

    return productPage.map(productMapper::toProductDTO);
  }

  @Override
  public List<ProductDTO> getRelatedProducts(
      Integer subCategoryId, UUID excludedId, Pageable pageable) {
    Page<Product> page =
        productRepository.findBySubCategoryIdAndIdNot(subCategoryId, excludedId, pageable);

      return page.getContent().stream()
          .map(productMapper::toProductDTO)
          .peek(dto -> dto.setImage(dto.getImage().split(",")[0]))
          .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ProductDTO updateProduct(UpdateProductRequest updateProduct, UUID productId)
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

    return productMapper.toProductDTO(productRepository.save(product));
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
