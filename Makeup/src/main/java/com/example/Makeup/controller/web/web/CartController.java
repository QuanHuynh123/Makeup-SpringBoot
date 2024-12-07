package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.CartDTO;
import com.example.Makeup.dto.CartItemDTO;
import com.example.Makeup.dto.UserDTO;
import com.example.Makeup.entity.Cart;
import com.example.Makeup.service.CartItemService;
import com.example.Makeup.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @GetMapping("/home")
    public String cart(Model model, HttpSession session) {
        try {
            UserDTO user = (UserDTO) session.getAttribute("user");

            if (user != null) {
                return "user/cart";
            } else {
                return "redirect:/login";
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Đã xảy ra lỗi trong quá trình lấy giỏ hàng.");
            return "error";
        }
    }

    @GetMapping("/checkOut")
    public String checkOut(Model model) {
        return "user/checkOut";
    }
}
