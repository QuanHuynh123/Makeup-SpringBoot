package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.*;
import com.example.Makeup.dto.response.CartItemResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.security.JWTProvider;
import com.example.Makeup.service.ICartItemService;
import com.example.Makeup.service.ICartService;
import com.example.Makeup.service.IUserService;
import com.example.Makeup.service.common.CacheCategoryService;
import com.example.Makeup.utils.SecurityUserUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
  public void addInformationUser(Model model) {
    try {
      UserDTO currentUser = SecurityUserUtil.getCurrentUser();
      model.addAttribute("user", currentUser);
    } catch (AppException e) {
      // handle when user don't log in
      model.addAttribute("user", null);
      log.info("No authenticated user found for web request.");
    }
  }

  @ModelAttribute("cartAttributes")
  public void addCartAndMiniCart(Model model) {
    model.addAttribute("cartItems", null);
    model.addAttribute("cart", null);
    model.addAttribute("countCart", 0);
    model.addAttribute("error", "Giỏ hàng trống. Vui lòng thêm sản phẩm.");

    UserDTO userDTO = null;
    try {
      userDTO = SecurityUserUtil.getCurrentUser();
    } catch (AppException ex) {
      log.warn("User not authenticated, cannot load cart.");
      return;
    }

    try {
      CartDTO cart = cartService.getCart(userDTO.getId());
      if (cart != null && cart.getId() != null) {
        List<CartItemResponse> cartItems = cartItemService.getCartItemByCartId();
        int count = (cartItems != null) ? cartItems.size() : 0;

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cart", cart);
        model.addAttribute("countCart", count);
        model.addAttribute("error", null);
      }
    } catch (Exception ex) {
      log.error("Error loading cart for userId: {}, exception: {}", userDTO.getId(), ex.getMessage());
      model.addAttribute("error", "Có lỗi xảy ra khi tải giỏ hàng.");
    }
  }

  @ModelAttribute("categoryAttributes")
  public void addCategoryHeaderCosplay(Model model) {
    try {
      List<CategoryDTO> categoryDTOS = cacheCategoryService.cacheAllCategory();

      if (!categoryDTOS.isEmpty()) {
        model.addAttribute("categories", categoryDTOS);
      } else {
        log.warn("Failed to load categories: {}", categoryDTOS);
      }
    } catch (Exception ex) {
      log.error("Exception while loading categories: {}", ex.getMessage(), ex);
    }
  }
}
