package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.SubCategoryDTO;
import com.example.Makeup.dto.response.ShortProductListResponse;
import com.example.Makeup.service.ISubCategoryService;
import com.example.Makeup.service.common.CacheProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cosplay")
public class CosplayController {

  private final CacheProductService cacheProductService;
  private final ISubCategoryService subCategoryServiceImpl;

  @GetMapping("/home")
  public String home(Model model) {
    List<ShortProductListResponse> hotProducts = cacheProductService.cacheHotProducts().getResult();
    List<ShortProductListResponse> newProducts = cacheProductService.cacheNewProducts().getResult();
    List<ShortProductListResponse> customerShowProducts =
        cacheProductService.cacheCustomerShow().getResult();
    model.addAttribute("hotProducts", hotProducts);
    model.addAttribute("newProducts", newProducts);
    model.addAttribute("customerShowProducts", customerShowProducts);
    return "user/cosplay";
  }

  @GetMapping({"/category/{id}", "/search"})
  public String categoryOrSearch(
      Model model,
      @PathVariable(value = "id", required = false) Integer id,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "searchKey", required = false) String search) {
    SubCategoryDTO categoryDTO = null;
    if (id != null) categoryDTO = subCategoryServiceImpl.findById(id).getResult();

    model.addAttribute("nameSub", categoryDTO != null ? categoryDTO.getName() : search);
    model.addAttribute("currentPage", 0);
    model.addAttribute("categoryId", id);
    model.addAttribute("searchKey", search);

    return "user/listviewProduct";
  }
}
