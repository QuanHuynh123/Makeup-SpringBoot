package com.example.Makeup.service;

import com.example.Makeup.dto.CreateProductDTO;
import com.example.Makeup.dto.ProductDTO;
import com.example.Makeup.entity.Product;
import com.example.Makeup.entity.SubCategory;
import com.example.Makeup.repository.ProductRepository;
import com.example.Makeup.repository.SubCategoryRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    SubCategoryService subCategoryService;
    
    public ProductDTO findById(int id){
        Optional<Product> opt = productRepository.findById(id);
        
        if (opt.isEmpty()) {
            return null;
        }
        
        Product product = opt.get();
        
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setNameProduct(product.getNameProduct());
        dto.setDescribe(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setSize(product.getSize());
        dto.setStatus(product.isStatus());
        dto.setSubCategoryId(product.getSubCategory().getId());
        dto.setImageList(product.getImageList());
        
        return dto;
    }
    
    public List<ProductDTO> getProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> {
            ProductDTO pdto = new ProductDTO();
            pdto.setId(product.getId());
            pdto.setNameProduct(product.getNameProduct());
            pdto.setDescribe(product.getDescription());
            pdto.setPrice(product.getPrice());
            pdto.setStatus(product.isStatus());
            pdto.setSize(product.getSize());
            pdto.setSubCategoryId(product.getSubCategory().getId());
            pdto.setImageList(product.getImageList());
            return pdto;
        }).toList();
    }
    
    public Product create(CreateProductDTO productDTO) throws IOException{
        SubCategory subCategory = subCategoryService.findById(productDTO.getSubCategoryId());
        
        Product product = new Product();
        product.setNameProduct(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setSize(productDTO.getSize());
        product.setPrice(productDTO.getPrice());
        product.setSubCategory(subCategory);
        
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
    
    public Product edit(CreateProductDTO productDTO, int id) throws IOException{
        SubCategory subCategory = subCategoryService.findById(productDTO.getSubCategoryId());
        
        Optional<Product> opt = productRepository.findById(id);
        
        if (opt.isEmpty()) {
            return null;
        }
        
        Product product = opt.get();
        product.setNameProduct(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStatus(productDTO.isStatus());
        product.setSize(productDTO.getSize());
        product.setSubCategory(subCategory);
        
        if (productDTO.getFiles() != null) {
            for (String image: product.getImageList()) {
                boolean deleted = deleteImage(image);
            }

            List<String> imagePaths = new ArrayList<>();
            for(MultipartFile file: productDTO.getFiles()){
                String path = saveImage(file);
                imagePaths.add(path);
            }
            product.setImage(String.join(",", imagePaths));
        }
        
        return productRepository.save(product);
    }
    
    private String saveImage(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images/";

        file.transferTo(new java.io.File(uploadDir + "/" + fileName));
        return "/images/" + fileName;
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
