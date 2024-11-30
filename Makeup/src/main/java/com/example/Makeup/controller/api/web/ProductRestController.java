package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.CreateProductDTO;
import com.example.Makeup.dto.ProductDTO;
import com.example.Makeup.entity.Product;
import com.example.Makeup.service.ProductService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/products")
public class ProductRestController {
    @Autowired
    ProductService productService;
    
    @PostMapping("/create")
    public ResponseEntity<String> create(@ModelAttribute CreateProductDTO producDTO) throws IOException{
        Product createdProduct = productService.create(producDTO);
        return ResponseEntity.ok("Tạo sản phẩm mới thành công");
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id){
        boolean result = productService.delete(id);

        if (result) 
            return ResponseEntity.ok("Xóa sản phẩm thành công");
        else
            return ResponseEntity.status(500).body("Xóa sản phẩm thất bại");
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> edit(@ModelAttribute CreateProductDTO productDTO, @PathVariable("id") int id) throws IOException{
//        Product product = productService.edit(productDTO, id);
        
//        if (product != null) {
//            return ResponseEntity.ok("Sửa sản phẩm thành công");
//        }
        return ResponseEntity.status(500).body("Sửa sản phẩm thất bại");
    }
}
