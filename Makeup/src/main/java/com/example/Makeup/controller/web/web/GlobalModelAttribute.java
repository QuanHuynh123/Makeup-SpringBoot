package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.*;
import com.example.Makeup.entity.Account;
import com.example.Makeup.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalModelAttribute {

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    FeedBackService feedBackService;

    @Autowired
    UserService userService;

    @ModelAttribute
    public void addSessionUser(HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.equals("anonymousUser")){
            UserDTO userDTO = userService.getInforUser(authentication.getName());
            session.setAttribute("user", userDTO);
        }
    }

    @ModelAttribute
    public void addUserLogin(Model model , HttpSession session ){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session User: " + userDTO); // Kiểm tra giá trị của userDTO
        if (userDTO != null) {
            model.addAttribute("user", userDTO);
        }
    }

    @ModelAttribute
    public void addCartMini(Model model , HttpSession session){
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            int userId = user.getId();  // Lấy userId từ session

            CartDTO cart = cartService.getCart(userId);
            List<CartItemDTO> cartItemDTOS = cartItemService.getCartItemByCartId(cart.getId());

            int count = cartService.countCartItem(cart.getId());

            if (cartItemDTOS.isEmpty())
                model.addAttribute("error","Bạn chưa thêm sản phẩm nào vào giỏ hàng!");

            model.addAttribute("cartItems", cartItemDTOS);
            model.addAttribute("cart", cart);
            model.addAttribute("countCart",count);
        }
    }

    @ModelAttribute
    public void addHeader(Model model ){
        List<CategoryDTO> categories = categoryService.getAllCategory();
        model.addAttribute("category",categories);
    }


    @ModelAttribute
    public void addFeedGoodFeedBack(Model model){
        List<FeedBackDTO> feedBackDTOS = feedBackService.getGoodFeedback(4);
        model.addAttribute("feedbacks", feedBackDTOS);
    }
}
