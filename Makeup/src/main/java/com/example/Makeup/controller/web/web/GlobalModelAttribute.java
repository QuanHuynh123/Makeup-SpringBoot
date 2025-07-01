package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.*;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.security.JWTProvider;
import com.example.Makeup.service.ICartItemService;
import com.example.Makeup.service.ICartService;
import com.example.Makeup.service.ICategoryService;
import com.example.Makeup.service.IUserService;
import com.example.Makeup.service.common.CacheCategoryService;
import com.example.Makeup.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.UUID;

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
        if (token != null && jwtProvider.isTokenValid(token)) { // Use isTokenValid to check if the token is valid
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
    public void addCartAndMiniCart(Model model, @ModelAttribute("user") UserDTO userDTO) {
        if (userDTO != null && userDTO.getId() != null) {
            UUID userId = userDTO.getId();
            try {
                ApiResponse<CartDTO> cart = cartService.getCart(userId);
                if (cart.getCode() == 200) {
                    ApiResponse<List<CartItemDTO>> cartItemDTOS = cartItemService.getCartItemByCartId(cart.getResult().getId());
                    int count = cartItemDTOS.getResult() != null ? cartItemDTOS.getResult().size() : 0;
                    if (cartItemDTOS.getCode() == 200 && cart.getResult() != null) {
                        model.addAttribute("cartItems", cartItemDTOS.getResult());
                    }
                    model.addAttribute("cart", cart.getResult());
                    model.addAttribute("countCart", count);
                } else {
                    model.addAttribute("error", "Bạn chưa có giỏ hàng nào.");
                    model.addAttribute("countCart", 0);
                }
            } catch (Exception ex) {
                log.error("Error loading cart for userId: {}, exception: {}", userId, ex.getMessage());
                model.addAttribute("error", "Lỗi khi tải giỏ hàng.");
            }
        }
    }

    @ModelAttribute("categoryAttributes")
    public void addCategoryHeaderCosplay(Model model ){
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
