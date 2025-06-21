package com.example.Makeup.service;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.request.CreateProductRequest;
import com.example.Makeup.dto.request.UpdateProductRequest;
import com.example.Makeup.entity.Product;
import com.example.Makeup.entity.SubCategory;
import com.example.Makeup.enums.ApiResponse;
import com.example.Makeup.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.ProductMapper;
import com.example.Makeup.repository.ProductRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductMapper productMapper;


    public ApiResponse<ProductDTO> findProductById(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return ApiResponse.<ProductDTO>builder()
                .code(200)
                .message("Product found")
                .result(productMapper.toProductDTO(product))
                .build();
    }
    
    public ApiResponse<List<ProductDTO>> getAllProducts(){
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return ApiResponse.<List<ProductDTO>>builder()
                .code(200)
                .message("Products found")
                .result(products.stream()
                        .map(productMapper::toProductDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public ApiResponse<List<ProductDTO>> getHotProducts() {
        List<Product> products = productRepository.findTopRentalCount(PageRequest.of(0,12));
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return ApiResponse.<List<ProductDTO>>builder()
                .code(200)
                .message("Products found")
                .result(products.stream()
                        .map(productMapper::toProductDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public ApiResponse<List<ProductDTO>> getNewProducts() {
        List<Product> products = productRepository.findTopCreatedAt(PageRequest.of(0,8));
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return ApiResponse.<List<ProductDTO>>builder()
                .code(200)
                .message("Products found")
                .result(products.stream()
                        .map(productMapper::toProductDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public ApiResponse<ProductDTO> createProduct(CreateProductRequest createProduct) throws IOException{
        SubCategory subCategory = subCategoryRepository.findById(createProduct.getSubCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));

        LocalDate localDate = LocalDate.now();

        Product product = new Product();
        product.setNameProduct(createProduct.getName());
        product.setDescription(createProduct.getDescription());
        product.setSize(createProduct.getSize());
        product.setPrice(createProduct.getPrice());
        product.setSubCategory(subCategory);
        product.setRentalCount(0);
        product.setCurrentQuantity(createProduct.getQuantity());

        List<String> imagePaths = new ArrayList<>();
        for(MultipartFile file: createProduct.getFiles()){
            String path = saveImage(file);
            imagePaths.add(path);
        }
        product.setImage(String.join(",", imagePaths));

        return ApiResponse.<ProductDTO>builder()
                .code(200)
                .message("Product created successfully")
                .result(productMapper.toProductDTO(productRepository.save(product)))
                .build();
    }

    @Transactional
    public ApiResponse<String> deleteProduct(UUID productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return ApiResponse.<String>builder()
                    .code(200)
                    .message("Product deleted successfully")
                    .result("Product with ID " + productId + " deleted successfully.")
                    .build();
        }else {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    public ApiResponse<Page<ProductDTO>> getProductBySubcategoryId(int subCategoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // Tạo đối tượng phân trang
        Page<Product> productPage = productRepository.findBySubCategoryId(subCategoryId, pageable);


        if (productPage.isEmpty()) {
            throw new AppException(ErrorCode.IS_EMPTY);
        }

        return ApiResponse.<Page<ProductDTO>>builder()
                .code(200)
                .message("Products found")
                .result(productPage.map(productMapper::toProductDTO))
                .build();
    }

    public int countProductsBySubcategoryId(int subCategoryId){
        return productRepository.countProductsBySubcategoryId(subCategoryId);
    }

    @Transactional
    public ApiResponse<ProductDTO> updateProduct(UpdateProductRequest updateProduct, UUID productId) throws IOException{
        SubCategory subCategory = subCategoryRepository.findById(updateProduct.getSubCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setNameProduct(updateProduct.getNameProduct());
        product.setDescription(updateProduct.getDescription());
        product.setPrice(updateProduct.getPrice());
        product.setStatus(updateProduct.isStatus());
        product.setSize(updateProduct.getSize());
        product.setSubCategory(subCategory);
        String[] images = product.getImage().split(",");

        if (updateProduct.getImage() != null) {
            for (String image: images) {
                deleteImage(image);
            }

            List<String> imagePaths = new ArrayList<>();
            for(MultipartFile file: updateProduct.getFiles()){
                String path = saveImage(file);
                imagePaths.add(path);
            }
            product.setImage(String.join(",", imagePaths));
        }

        return ApiResponse.<ProductDTO>builder()
                .code(200)
                .message("Product updated successfully")
                .result(productMapper.toProductDTO(productRepository.save(product)))
                .build();
    }

    private String saveImage(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images/product";
        file.transferTo(new java.io.File(uploadDir + "/" + fileName));
        return "/images/product/" + fileName;
    }

    private boolean deleteImage(String imagePath){
        String filePath = Paths.get("src/main/resources/static", imagePath).toString();

        File file = new File(filePath);

        if (!file.exists())
            return false;

        boolean deleted = file.delete();
        if (deleted) {
            System.out.println("File deleted successfully: " + filePath);
            return true;
        } else {
            System.out.println("Failed to delete file: " + filePath);
            return false;
        }

    }
}
