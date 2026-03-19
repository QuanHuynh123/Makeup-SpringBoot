package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.*;
import com.example.Makeup.dto.response.CartItemResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.service.ICartItemService;
import com.example.Makeup.service.ICartService;
import com.example.Makeup.service.common.CacheCategoryService;
import com.example.Makeup.utils.SecurityUserUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
@RequiredArgsConstructor
public class GlobalModelAttribute {

  private final ICartService cartService;
  private final ICartItemService cartItemService;
  private final CacheCategoryService cacheCategoryService;

  @ModelAttribute
  public void addGlobalAttributes(Model model) {
    UserDTO currentUser = null;
    try {
      currentUser = SecurityUserUtil.getCurrentUser();
    } catch (AppException e) {
      log.debug("No authenticated user found for web request.");
    }
    model.addAttribute("user", currentUser);

    // Default cart state
    model.addAttribute("cartItems", null);
    model.addAttribute("cart", null);
    model.addAttribute("countCart", 0);
    model.addAttribute("error", "Giỏ hàng trống. Vui lòng thêm sản phẩm.");

    if (currentUser == null) return;

    try {
      CartDTO cart = cartService.getCart(currentUser.getId());
      if (cart != null && cart.getId() != null) {
        List<CartItemResponse> cartItems = cartItemService.getCartItemByCartId();
        int count = (cartItems != null) ? cartItems.size() : 0;
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cart", cart);
        model.addAttribute("countCart", count);
        model.addAttribute("error", null);
      }
    } catch (Exception ex) {
      log.error("Error loading cart for userId: {}, exception: {}", currentUser.getId(), ex.getMessage());
      model.addAttribute("error", "Có lỗi xảy ra khi tải giỏ hàng.");
    }
  }

  @ModelAttribute
  public void addCategoryHeaderCosplay(Model model) {
    try {
      List<CategoryDTO> categoryDTOS = cacheCategoryService.cacheAllCategory();
      model.addAttribute("categories", categoryDTOS);
    } catch (Exception ex) {
      log.error("Exception while loading categories: {}", ex.getMessage(), ex);
    }
  }
}
