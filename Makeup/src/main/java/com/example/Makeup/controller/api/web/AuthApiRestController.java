package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.request.LoginRequest;
import com.example.Makeup.dto.request.RegisterRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IAccountService;
import com.example.Makeup.service.IRefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    public ApiResponse<?> createToken(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        log.info("Creating token for user: {}", loginRequest.getUsername());
        ApiResponse<String> apiResponse = accountService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        String token = apiResponse.getResult();

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(cookieMaxAge);
        response.addCookie(cookie);
        return apiResponse;
    }

    @PostMapping("/register")
    public ApiResponse<String> createAccount(@RequestBody RegisterRequest registerRequest) {
        return accountService.signUp(registerRequest);
    }


//    @PostMapping("/refresh-token")
//    public ApiResponse<String> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
//        return refreshTokenService.refreshToken(refreshTokenRequest.getRefreshToken());
//    }


}
