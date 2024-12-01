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
                int userId = user.getId();  // Lấy userId từ session

                // Gọi CartService để lấy cart của người dùng dựa trên userId
                CartDTO cart = cartService.getCart(userId);  // Giả sử bạn có phương thức này trong CartService
                List<CartItemDTO> cartItemDTOS = cartItemService.getCartItemByCartId(cart.getId());

                if (cartItemDTOS.isEmpty())
                    model.addAttribute("error","null");

                model.addAttribute("cartItem", cartItemDTOS);
                model.addAttribute("cart", cart);

                // Trả về view "cart"
                return "user/cart";
            } else {
                return "redirect:/login";
            }

        } catch (Exception e) {
            // Ghi log hoặc xử lý lỗi ở đây
            e.printStackTrace();
            model.addAttribute("errorMessage", "Đã xảy ra lỗi trong quá trình lấy giỏ hàng.");
            return "error";  // Trả về một trang lỗi hoặc trang thích hợp
        }
    }
}
