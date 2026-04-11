package com.example.Makeup.security;

import com.example.Makeup.service.common.RateLimitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class ApiRateLimitFilter extends OncePerRequestFilter {

  private static final long WINDOW_MILLIS = 60_000L;
  private static final int CART_GET_LIMIT = 90;
  private static final int CART_MUTATION_LIMIT = 50;
  private static final int ORDER_PLACE_LIMIT = 20;
  private static final int ORDER_PAYMENT_LIMIT = 25;
  private static final int CHAT_LIMIT = 20;

  private final ObjectMapper objectMapper;
  private final RateLimitService rateLimitService;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    return !path.startsWith("/api/");
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    RateLimitRule rule = resolveRule(request);
    if (rule == null) {
      filterChain.doFilter(request, response);
      return;
    }

    String principal = resolvePrincipal(request);
    String key = "api:" + principal + "|" + rule.bucket();

    if (rateLimitService.isRateLimited(key, rule.limit(), Duration.ofMillis(WINDOW_MILLIS))) {
      writeTooManyRequests(response);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private RateLimitRule resolveRule(HttpServletRequest request) {
    String path = request.getRequestURI();
    String method = request.getMethod();

    if ("/api/cart".equals(path) && "GET".equalsIgnoreCase(method)) {
      return new RateLimitRule("cart:read", CART_GET_LIMIT);
    }
    if (path.startsWith("/api/cart") && !"GET".equalsIgnoreCase(method)) {
      return new RateLimitRule("cart:write", CART_MUTATION_LIMIT);
    }
    if ("/api/orders/place".equals(path) && "POST".equalsIgnoreCase(method)) {
      return new RateLimitRule("order:place", ORDER_PLACE_LIMIT);
    }
    if ("/api/order/submit-order".equals(path) && "POST".equalsIgnoreCase(method)) {
      return new RateLimitRule("order:payment", ORDER_PAYMENT_LIMIT);
    }
    if (path.startsWith("/api/chat")) {
      return new RateLimitRule("chat:general", CHAT_LIMIT);
    }

    return null;
  }

  private String resolvePrincipal(HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      if (username != null && !username.isBlank() && !"anonymousUser".equals(username)) {
        return "user:" + username;
      }
    }
    return "ip:" + request.getRemoteAddr();
  }

  private void writeTooManyRequests(HttpServletResponse response) throws IOException {
    response.setStatus(429);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    objectMapper.writeValue(
        response.getWriter(),
        Map.of(
            "code", 429,
            "message", "Too many requests. Please try again in a moment.",
            "timestamp", Instant.now().toString()));
  }

  private record RateLimitRule(String bucket, int limit) {}
}
