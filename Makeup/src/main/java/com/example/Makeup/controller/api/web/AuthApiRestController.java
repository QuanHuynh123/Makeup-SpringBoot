package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.request.LoginRequest;
import com.example.Makeup.dto.request.RegisterRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IAccountService;
import com.example.Makeup.service.IRefreshTokenService;
import com.example.Makeup.service.common.RateLimitService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthApiRestController {

    @Value("${cookie.maxAge}")
    private int cookieMaxAge;
    private final IAccountService accountService;
    private final RateLimitService rateLimitService;

    @PostMapping("/login")
    public ApiResponse<?> createToken(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String username = loginRequest.getUsername();
        // String ip = "";
        String rateLimitKey = "login:" + username;

        if (rateLimitService.isRateLimited(rateLimitKey, 5, Duration.ofMinutes(1))) {
            log.warn("Too many login attempts for user: {}", username);
            return ApiResponse.error(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many login attempts. Please try again later.");
        }

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
