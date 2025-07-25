package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProductDetailController {

    private final IProductService productService;

    @GetMapping("/productDetail/{id}")
    public String home(Model model, @PathVariable("id") UUID idProduct) {
        ProductDTO product = productService.findProductById(idProduct).getResult();
        model.addAttribute("product", product);

        List<String> images = Arrays.stream(product.getImage().split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        model.addAttribute("images", images);

        List<ProductDTO> relatedProducts = productService
                .getRelatedProducts(product.getSubCategoryId(), idProduct, PageRequest.of(0, 4))
                .getResult();
        model.addAttribute("relatedProducts", relatedProducts);

        return "user/productDetail";
    }

}
