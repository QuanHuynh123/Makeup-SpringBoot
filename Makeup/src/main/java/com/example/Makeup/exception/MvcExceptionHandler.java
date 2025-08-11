package com.example.Makeup.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(
    basePackages = "com.example.Makeup.controller.web") // handle exceptions for web controllers
@Slf4j
public class MvcExceptionHandler {

  @ExceptionHandler(AppException.class)
  public String handleAppException(AppException e, Model model, HttpServletRequest request) {
    log.error("AppException tại {}: {}", request.getRequestURI(), e.getMessage());
    model.addAttribute("errorMessage", e.getErrorCode().getMessage());
    return "error/custom-error";
  }

  @ExceptionHandler(Exception.class)
  public String handleOtherExceptions(Exception e, Model model, HttpServletRequest request) {
    log.error("Exception tại {}: {}", request.getRequestURI(), e.getMessage());
    model.addAttribute("errorMessage", "Đã xảy ra lỗi không xác định.");
    return "error/custom-error";
  }
}
