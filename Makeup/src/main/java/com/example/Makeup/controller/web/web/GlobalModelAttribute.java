package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.*;
import com.example.Makeup.enums.ApiResponse;
import com.example.Makeup.service.*;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final CategoryService categoryService;
    private final FeedBackService feedBackService;
    private final UserService userService;
    private final OrderService orderService;
    private final JWTService jwtService;

    @ModelAttribute
    public void addInformationUser(Model model, HttpServletRequest request) {
        log.info("Welcome User");
        String token = getJwtFromRequest(request);
        if (token != null && jwtService.isTokenValid(token)) { // Use isTokenValid to check if the token is valid
            log.info("Token is valid, extracting user information");
            try {
                String userName = jwtService.extractUserName(token);
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
                    int count = cartService.countCartItem(cart.getResult().getId());
                    if (cartItemDTOS.getCode() == 200) {
                        model.addAttribute("cartItems", cartItemDTOS.getResult());
                    }
                    model.addAttribute("cart", cart.getResult());
                    model.addAttribute("countCart", count);
                } else {
                    log.warn("Failed to load cart for userId: {}, code: {}", userId, cart.getCode());
                }
            } catch (Exception ex) {
                log.error("Error loading cart for userId: {}, exception: {}", userId, ex.getMessage());
            }
        }
    }

    @ModelAttribute("categoryAttributes")
    public void addCategoryHeaderCosplay(Model model ){
        ApiResponse<List<CategoryDTO>> categories = categoryService.getAllCategory();
        if (categories.getCode() == 200) {
            model.addAttribute("category", categories.getResult());
        } else {
            log.warn("Failed to load categories");
        }
    }

    @ModelAttribute("feedbackAttributes")
    public void addFeedGoodFeedBack(Model model){
        ApiResponse<List<FeedBackDTO>> feedBackDTOS = feedBackService.getGoodFeedback(4);
        if (feedBackDTOS.getCode() == 200) {
            model.addAttribute("feedbacks", feedBackDTOS.getResult());
        } else {
            log.warn("Failed to load feedback");
        }
    }
}
