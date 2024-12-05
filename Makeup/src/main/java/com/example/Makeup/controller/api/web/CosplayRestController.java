package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.ProductDTO;
import com.example.Makeup.entity.Product;
import com.example.Makeup.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CosplayRestController {

    @Autowired
    private ProductService productService;

    // API trả về danh sách sản phẩm và thông tin phân trang dưới dạng JSON khi gọi AJAX
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCategoryProducts(@PathVariable("id") int id,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        System.out.println("ID: " + id + ", Page: " + page + ", Size: " + size);
        Page<ProductDTO> productsPage  = productService.getProductBySubcategoryId(id, page, size);
        // Tạo Map để trả về dữ liệu phân trang và danh sách sản phẩm
        Map<String, Object> response = new HashMap<>();
        response.put("products", productsPage.getContent());  // Danh sách sản phẩm
        response.put("currentPage", productsPage.getNumber());  // Trang hiện tại
        response.put("totalPages", productsPage.getTotalPages());
        response.put("totalItems", productsPage.getTotalElements());

        // Trả về danh sách sản phẩm và thông tin phân trang dưới dạng JSON
        return ResponseEntity.ok(response);
    }
}


