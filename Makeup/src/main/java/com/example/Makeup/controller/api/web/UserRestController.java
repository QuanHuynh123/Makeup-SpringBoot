package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.enums.ApiResponse;
import com.example.Makeup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/create")
    public ApiResponse<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return  userService.createUser(userDTO);
    }

    @PostMapping("/profile/update")
    public ApiResponse<UserDTO> updateProfile(@RequestBody UserDTO userDTO){
        return userService.updateUser(userDTO.getAccountId(), userDTO.getFullName(), userDTO.getEmail(), userDTO.getAddress());
    }
}