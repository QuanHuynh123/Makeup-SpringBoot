package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

    private final IUserService userService;

    @PostMapping("/create")
    public ApiResponse<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return  userService.createUser(userDTO);
    }

    @PostMapping("/profile/update")
    public ApiResponse<UserDTO> updateProfile(@RequestBody UserDTO userDTO){
        return userService.updateUser(userDTO.getAccountId(), userDTO.getFullName(), userDTO.getEmail(), userDTO.getAddress());
    }
}