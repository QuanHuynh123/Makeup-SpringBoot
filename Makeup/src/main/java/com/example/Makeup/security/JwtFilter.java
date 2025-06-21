package com.example.Makeup.security;

import com.example.Makeup.service.AccountService;
import com.example.Makeup.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final AccountService accountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = null ;
        String username = null ;

        String authHeader = request.getHeader("Authorization");
        // Check token in Authorization header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        // Check token in cookies if not found in header
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("jwt".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }

        if (token != null && jwtService.isTokenValid(token)) {
            username = jwtService.extractUserName(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                if (jwtService.isTokenValid(token)) {
                    Integer roleId = jwtService.extractRole(token); // Get roleId from the token
                    String role = mapRoleToAuthority(roleId);

                    // Crate List of authorities
                    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

                    // Create Context with user details
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }else {
                    log.warn("Token invalid or expired for username: {}, proceeding as anonymous", username);
                }

            } catch (Exception e) {
                logger.warn("Cannot set user authentication");
            }
        }
        filterChain.doFilter(request,response);
    }

    private String mapRoleToAuthority(Integer roleId) {
        switch (roleId) {
            case 1:
                return "ROLE_ADMIN";
            case 2:
                return "ROLE_USER";
            case 3:
                return "ROLE_STAFF";
            default:
                throw new IllegalArgumentException("Unknown role ID: " + roleId);
        }
    }
}
