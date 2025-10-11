package com.example.Makeup.security;

import com.example.Makeup.service.common.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

  private final RefreshTokenService refreshTokenService;

  @Override
  public void logout(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

    String refreshToken = null;
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if ("refresh_token".equals(cookie.getName())) {
          refreshToken = cookie.getValue();
          break;
        }
      }
    }

    if (refreshToken != null) {
      try {
        refreshTokenService.revokeRefreshToken(refreshToken);
        log.info("Revoked refresh token in Redis: {}", refreshToken);
      } catch (Exception e) {
        log.warn("Failed to revoke refresh token: {}", e.getMessage());
      }
    }

    clearCookie(response, "refresh_token");
    clearCookie(response, "access_token");

    SecurityContextHolder.clearContext();
    log.info("SecurityContext cleared");
  }

  private void clearCookie(HttpServletResponse response, String name) {
    Cookie cookie = new Cookie(name, null);
    cookie.setHttpOnly(true);
    cookie.setSecure(true); // nếu deploy HTTPS
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
}
