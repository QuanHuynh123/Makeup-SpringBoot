package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.*;
import com.example.Makeup.dto.response.CartItemResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
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
  private final IUserService userService;
  private final JWTProvider jwtProvider;
  private final CacheCategoryService cacheCategoryService;

  @ModelAttribute
  public void addInformationUser(Model model, HttpServletRequest request) {
    log.info("Welcome User");
    String token = getJwtFromRequest(request);
    if (token != null
        && jwtProvider.isTokenValid(token)) { // Use isTokenValid to check if the token is valid
      log.info("Token is valid, extracting user information");
      try {
        String userName = jwtProvider.extractUserName(token);
        log.info("Extracted username: {}", userName);
        UserDTO userDTO = userService.getUserDetailByUserName(userName).getResult();
        model.addAttribute("user", userDTO != null ? userDTO : null);
      } catch (Exception ex) {
        log.error("Invalid token or user not found: {}", ex.getMessage());
        model.addAttribute("user", null);
      }
    } else {
      log.info("Welcome Anonymous");
      model.addAttribute("user", null);
    }
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    log.debug("get JWT from request");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    log.debug("No JWT found in Authorization header");
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if (cookie != null && "jwt".equals(cookie.getName())) {
          String value = cookie.getValue();
          if (value != null) {
            return value;
          }
        }
      }
      log.debug("No JWT found in cookies");
    } else {
      log.debug("No cookies in request");
    }
    return null;
  }

  @ModelAttribute("cartAttributes")
  public void addCartAndMiniCart(Model model) {
    UserDTO userDTO = null;
    try {
      userDTO = SecurityUserUtil.getCurrentUser();
    } catch (Exception e) {
      userDTO = null;
    }

    if (userDTO != null) {
      try {
        CartDTO cart = cartService.getCart(userDTO.getId()).getResult();
        if (cart != null && cart.getId() != null) {
          List<CartItemResponse> cartItemDTOS = cartItemService.getCartItemByCartId().getResult();
          int count = cartItemDTOS != null ? cartItemDTOS.size() : 0;
          model.addAttribute("cartItems", cartItemDTOS);
          model.addAttribute("cart", cart);
          model.addAttribute("countCart", count);
        } else {
          model.addAttribute("error", "Bạn chưa có giỏ hàng nào.");
          model.addAttribute("countCart", 0);
        }
      } catch (Exception ex) {
        log.error(
            "Error loading cart for userId: {}, exception: {}", userDTO.getId(), ex.getMessage());
        model.addAttribute("error", "Giỏ hàng trống. Vui lòng thêm sản phẩm vào giỏ hàng.");
      }
    } else {
      log.warn("User ID is null, cannot load cart");
      model.addAttribute("error", "Không tìm thấy người dùng.");
      model.addAttribute("countCart", 0);
    }
  }

  @ModelAttribute("categoryAttributes")
  public void addCategoryHeaderCosplay(Model model) {
    try {
      ApiResponse<List<CategoryDTO>> categoryDTOS = cacheCategoryService.cacheAllCategory();

      if (categoryDTOS.getCode() == 200) {
        model.addAttribute("categories", categoryDTOS.getResult());
      } else {
        log.warn("Failed to load categories: {}", categoryDTOS.getMessage());
      }
    } catch (Exception ex) {
      log.error("Exception while loading categories: {}", ex.getMessage(), ex);
    }
  }
}
