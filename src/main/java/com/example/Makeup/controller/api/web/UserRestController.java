package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.request.UpdateProfileUserRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.security.JWTProvider;
import com.example.Makeup.service.IUserService;
import com.example.Makeup.utils.SecurityUserUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
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
    return ApiResponse.success("Create user success",userService.createUser(userDTO));
  }

  @PostMapping("/profile/update")
  public ApiResponse<UserDTO> updateProfile( @RequestBody UpdateProfileUserRequest profileUserRequest) {
    UserDTO currentUser = SecurityUserUtil.getCurrentUser();
    UUID accountId = currentUser.getId();

    return ApiResponse.success("Update user success", userService.updateUser(profileUserRequest, accountId));
  }
}
