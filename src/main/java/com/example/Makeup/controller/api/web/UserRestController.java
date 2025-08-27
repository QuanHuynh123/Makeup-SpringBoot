package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.request.UpdateProfileUserRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.security.JWTProvider;
import com.example.Makeup.service.IUserService;
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
  private final JWTProvider jwtProvider;

  @PostMapping("/create")
  public ApiResponse<UserDTO> createUser(@RequestBody UserDTO userDTO) {
    return userService.createUser(userDTO);
  }

  @PostMapping("/profile/update")
  public ApiResponse<UserDTO> updateProfile(
      @RequestBody UpdateProfileUserRequest profileUserRequest, HttpServletRequest request) {
    String jwt = null;

    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if ("jwt".equals(cookie.getName())) {
          jwt = cookie.getValue();
          break;
        }
      }
    }

    UUID accountId = jwtProvider.extractAccountIdAllowExpired(jwt);
    return userService.updateUser(profileUserRequest, accountId);
  }
}
