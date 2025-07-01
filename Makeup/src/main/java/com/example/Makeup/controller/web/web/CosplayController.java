package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.dto.model.SubCategoryDTO;
import com.example.Makeup.dto.response.ShortProductListResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.service.common.CacheProductService;
import com.example.Makeup.service.impl.ProductService;
import java.util.List;

import com.example.Makeup.service.impl.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cosplay")
public class CosplayController {

    private final CacheProductService cacheProductService;
    private final ProductService productService;
    private final SubCategoryService subCategoryService;
    
    @GetMapping("/home")
    public String home(Model model){
        List<ShortProductListResponse> hotProducts = cacheProductService.cacheHotProducts().getResult();
        List<ShortProductListResponse> newProducts = cacheProductService.cacheNewProducts().getResult();
        List<ShortProductListResponse> customerShowProducts = cacheProductService.cacheCustomerShow().getResult();
        model.addAttribute("hotProducts", hotProducts);
        model.addAttribute("newProducts", newProducts);
        model.addAttribute("customerShowProducts", customerShowProducts);
        return "user/cosplay";
    }

    @GetMapping("/category/{id}")
    public String category(Model model, @PathVariable("id") int id,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Page<ProductDTO> productPage = productService.getProductBySubcategoryId(id, page, size).getResult();
            int totalProducts = productService.countProductsBySubcategoryId(id);
            int totalPages = (int) Math.ceil((double) totalProducts / size);
            SubCategoryDTO subCategoryDTO = subCategoryService.findById(id).getResult();

            model.addAttribute("products", productPage.getContent());
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);
            model.addAttribute("categoryId", id);
            model.addAttribute("nameSub", subCategoryDTO.getName());

        } catch (AppException e) {
            model.addAttribute("nullProduct", "Không có sản phẩm");
        }
        return "user/listviewProduct";  // Return all products in the category if not call ajax
    }


}
