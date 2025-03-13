package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.CategoryDTO;
import com.example.Makeup.dto.ProductDTO;
import com.example.Makeup.dto.SubCategoryDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.service.CategoryService;
import com.example.Makeup.service.ProductService;
import java.util.List;

import com.example.Makeup.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping("/cosplay")
public class CosplayController {
    @Autowired
    ProductService productService;
    @Autowired
    SubCategoryService subCategoryService;
    
    @GetMapping("/home")
    public String home(Model model){
        List<ProductDTO> hotProducts = productService.getHotProducts();
        List<ProductDTO> newProducts = productService.getNewProducts();
        List<ProductDTO> randomCustomerShow = productService.getCustomerShowRandom();
        model.addAttribute("hotProducts", hotProducts);
        model.addAttribute("newProducts", newProducts);
        model.addAttribute("randomCustomerShow", randomCustomerShow);
        return "user/cosplay";
    }

    @GetMapping("/category/{id}")
    public String category(Model model, @PathVariable("id") int id,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Page<ProductDTO> productPage = productService.getProductBySubcategoryId(id, page, size);
            int totalProducts = productService.countProductsBySubcategoryId(id);
            int totalPages = (int) Math.ceil((double) totalProducts / size);
            SubCategoryDTO subCategoryDTO =  subCategoryService.findById(id);

            model.addAttribute("products", productPage.getContent());
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);
            model.addAttribute("categoryId", id);
            model.addAttribute("nameSub", subCategoryDTO.getName());

        } catch (AppException e) {
            model.addAttribute("nullProduct", "Không có sản phẩm");
        }
        return "user/listviewProduct";  // Trả về toàn bộ trang nếu không phải yêu cầu AJAX
    }


}
