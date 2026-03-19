package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.request.UpdateProfileUserRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IUserService;
import com.example.Makeup.utils.SecurityUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "API for user operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

  private final IUserService userService;

  @Operation(summary = "Create a new user", description = "Create a new user with the provided details")
  @PostMapping("/create")
  public ApiResponse<UserDTO> createUser(@RequestBody UserDTO userDTO) {
    return ApiResponse.success("Create user success",userService.createUser(userDTO));
  }

  @Operation(summary = "Update user profile", description = "Update the profile of the current user")
  @PatchMapping("/profile/update")
  public ApiResponse<UserDTO> updateProfile( @RequestBody UpdateProfileUserRequest profileUserRequest) {
    UserDTO currentUser = SecurityUserUtil.getCurrentUser();
    UUID accountId = currentUser.getId();

    return ApiResponse.success("Update user success", userService.updateUser(profileUserRequest, accountId));
  }
}
