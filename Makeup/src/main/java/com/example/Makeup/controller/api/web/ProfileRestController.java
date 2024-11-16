package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.UserDTO;
import com.example.Makeup.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class ProfileRestController {

    @Autowired
    UserService userService;

    @PostMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody UserDTO userDTO , HttpSession session){
        try {
            UserDTO userUpdateSucess  =  userService.updateInforUser(userDTO.getPhone(), userDTO.getFullName(), userDTO.getEmail(), userDTO.getAddress());
            session.setAttribute("user", userUpdateSucess);
            return ResponseEntity.ok("Cập nhật thành công!");
        }catch (Exception e ){
            return ResponseEntity.notFound().build();
        }
    }
}
