package com.example.Makeup.utils;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.exception.AppException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUserUtil {
    public static UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.USER_IN_AUTHENTICATED_NOT_FOUND);
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDTO user) {
            return user;
        }
        throw new AppException(ErrorCode.USER_IN_AUTHENTICATED_NOT_FOUND);
    }
}
