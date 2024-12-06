package com.example.Makeup.service;

import com.example.Makeup.dto.CreateProductDTO;
import com.example.Makeup.dto.ProductDTO;
import com.example.Makeup.dto.SubCategoryDTO;
import com.example.Makeup.entity.Product;
import com.example.Makeup.mapper.SubCategoryMapper;
import org.springframework.data.domain.Page;
import com.example.Makeup.entity.SubCategory;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.ProductMapper;
import com.example.Makeup.repository.ProductRepository;
import java.io.File;
import java.io.IOException;

import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    SubCategoryService subCategoryService;

    @Autowired
    SubCategoryMapper subCategoryMapper;

    @Autowired
    ProductMapper productMapper;
    public ProductDTO findById(int id){
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.toProductDTO(product);
    }
    
    public List<ProductDTO> getProducts(){
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return products.stream()
                    .map(productMapper::toProductDTO)
                    .collect(Collectors.toList());
    }

    public List<ProductDTO> getHotProducts() {
        List<Product> products = productRepository.findTopRentalCount(PageRequest.of(0,12));
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return products.stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getNewProducts() {
        List<Product> products = productRepository.findTopCreatedAt(PageRequest.of(0,8));
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return products.stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    public Product create(CreateProductDTO productDTO) throws IOException{
        SubCategoryDTO subCategoryDTO = subCategoryService.findById(productDTO.getSubCategoryId());

        LocalDate localDate = LocalDate.now();

        Product product = new Product();
        product.setNameProduct(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setSize(productDTO.getSize());
        product.setPrice(productDTO.getPrice());
        product.setSubCategory(subCategoryMapper.toSubCategoryEntity(subCategoryDTO));
        product.setRentalCount(0);
        product.setCreatedAt(Date.valueOf(localDate));

        List<String> imagePaths = new ArrayList<>();
        for(MultipartFile file: productDTO.getFiles()){
            String path = saveImage(file);
            imagePaths.add(path);
        }
        product.setImage(String.join(",", imagePaths));

        return productRepository.save(product);
    }

    public boolean delete(int id){
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Page<ProductDTO> getProductBySubcategoryId(int subCategoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // Tạo đối tượng phân trang
        Page<Product> productPage = productRepository.findBySubCategoryId(subCategoryId, pageable);

        // Kiểm tra nếu không có sản phẩm nào trong trang
        if (productPage.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND); // Ném exception nếu không có sản phẩm
        }

        // Chuyển đổi Page<Product> thành Page<ProductDTO>
        return productPage.map(productMapper::toProductDTO);
    }

    public int countProductsBySubcategoryId(int subCategoryId){
        return productRepository.countProductsBySubcategoryId(subCategoryId);
    }


    
//    public Product edit(CreateProductDTO productDTO, int id) throws IOException{
//        SubCategory subCategory = subCategoryService.findById(productDTO.getSubCategoryId());
//
//        Optional<Product> opt = productRepository.findById(id);
//
//        if (opt.isEmpty()) {
//            return null;
//        }
//
//        Product product = opt.get();
//        product.setNameProduct(productDTO.getName());
//        product.setDescription(productDTO.getDescription());
//        product.setPrice(productDTO.getPrice());
//        product.setStatus(productDTO.isStatus());
//        product.setSize(productDTO.getSize());
//        product.setSubCategory(subCategory);
//
//        if (productDTO.getFiles() != null) {
//            for (String image: product) {
//                boolean deleted = deleteImage(image);
//            }
//
//            List<String> imagePaths = new ArrayList<>();
//            for(MultipartFile file: productDTO.getFiles()){
//                String path = saveImage(file);
//                imagePaths.add(path);
//            }
//            product.setImage(String.join(",", imagePaths));
//        }
//
//        return productRepository.save(product);
//    }

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
            return true;
        } else
            return false;

    }
}
