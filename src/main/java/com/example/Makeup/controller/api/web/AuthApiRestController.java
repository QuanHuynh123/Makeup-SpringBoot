package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.request.LoginRequest;
import com.example.Makeup.dto.request.RegisterRequest;
import com.example.Makeup.dto.response.AuthResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IAccountService;
import com.example.Makeup.service.IRefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication API", description = "API for user authentication and registration")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthApiRestController {

  @Value("${cookie.maxAge}")
  private int cookieMaxAge;

  @Value("${cookie.secure:false}")
  private boolean cookieSecure;

  private final IAccountService accountService;
  private final IRefreshTokenService refreshTokenService;

  @Operation(summary = "User login", description = "Authenticate user and set tokens in cookies")
  @PostMapping("/login")
  public ApiResponse<String> login(
          @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

    AuthResponse apiResponse =
            accountService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    String accessToken = apiResponse.getAccessToken();
    String refreshToken = apiResponse.getRefreshToken();

      setAuthCookie(response, "refresh_token", refreshToken);
      setAuthCookie(response, "access_token", accessToken);


    return ApiResponse.success("Login success", accessToken) ;
  }

  @Operation(summary = "User registration", description = "Register a new user account")
  @PostMapping("/register")
  public ApiResponse<String> regis(@RequestBody RegisterRequest registerRequest) {
    return ApiResponse.success("Sign up success",accountService.signUp(registerRequest));
  }

  @Operation(summary = "Refresh token", description = "Refresh access token using refresh token from cookie")
  @PostMapping("/refresh")
  public ApiResponse<String> refreshToken(
          @CookieValue("refresh_token") String refreshToken,
          HttpServletResponse response){
    AuthResponse tokens = refreshTokenService.refreshToken(refreshToken);
    setAuthCookie(response, "refresh_token", tokens.getRefreshToken());
    setAuthCookie(response, "access_token", tokens.getAccessToken());
    return ApiResponse.success("Refresh success", tokens.getAccessToken());
  }

  private void setAuthCookie(HttpServletResponse response, String name, String token) {
    Cookie cookie = new Cookie(name, token);
    cookie.setHttpOnly(true);
    cookie.setSecure(cookieSecure);
    cookie.setPath("/");
    cookie.setMaxAge(cookieMaxAge);
    cookie.setAttribute("SameSite", "Strict");
    response.addCookie(cookie);
  }

}
