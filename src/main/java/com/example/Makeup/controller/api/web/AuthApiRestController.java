package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.request.LoginRequest;
import com.example.Makeup.dto.request.RegisterRequest;
import com.example.Makeup.dto.response.AuthResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IAccountService;
import com.example.Makeup.service.IRefreshTokenService;
import com.example.Makeup.service.common.RateLimitService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthApiRestController {

  @Value("${cookie.maxAge}")
  private int cookieMaxAge;

  private final IAccountService accountService;
  private final IRefreshTokenService refreshTokenService;


  @PostMapping("/login")
  public ApiResponse<String> login(
          @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

    AuthResponse apiResponse =
            accountService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    String accessToken = apiResponse.getAccessToken();
    String refreshToken = apiResponse.getRefreshToken();

    Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
    refreshCookie.setHttpOnly(true);
    refreshCookie.setSecure(false);
    refreshCookie.setPath("/");
    refreshCookie.setMaxAge(cookieMaxAge);
    refreshCookie.setAttribute("SameSite", "Strict");
    response.addCookie(refreshCookie);

    Cookie accessCookie = new Cookie("access_token", accessToken);
    accessCookie.setHttpOnly(true);
    accessCookie.setSecure(false);
    accessCookie.setPath("/");
    accessCookie.setMaxAge(cookieMaxAge);
    accessCookie.setAttribute("SameSite", "Strict");
    response.addCookie(accessCookie);


    return ApiResponse.success("Login success", accessToken) ;
  }

  @PostMapping("/register")
  public ApiResponse<String> regis(@RequestBody RegisterRequest registerRequest) {
    return ApiResponse.success("Sign up success",accountService.signUp(registerRequest));
  }

  @PostMapping("/refresh")
  public ApiResponse<?> refreshToken(@CookieValue("refresh_token") String refreshToken){
    return ApiResponse.success("Refresh success",refreshTokenService.refreshToken(refreshToken));
  }

}
