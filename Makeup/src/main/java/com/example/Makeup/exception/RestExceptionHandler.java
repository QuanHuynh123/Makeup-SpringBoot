package com.example.Makeup.exception;

import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(
    basePackages = "com.example.Makeup.controller.api") // handle exceptions for REST controllers
@Slf4j
public class RestExceptionHandler {

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ApiResponse<Object>> handleAppException(AppException e) {
    ErrorCode code = e.getErrorCode();
    return ResponseEntity.status(code.getStatusCode())
        .body(
            ApiResponse.builder()
                .code(code.getStatusCode().value())
                .message(code.getMessage())
                .result(null)
                .build());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException e) {
    log.error("Unexpected error: ", e);
    ErrorCode errorCode = ErrorCode.UNKNOWN_ERROR;
    return ResponseEntity.status(errorCode.getStatusCode())
        .body(
            ApiResponse.builder()
                .code(errorCode.getStatusCode().value())
                .message(errorCode.getMessage())
                .result(null)
                .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException e) {
    String msg = e.getBindingResult().getFieldError().getDefaultMessage();
    return ResponseEntity.badRequest()
        .body(
            ApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(msg)
                .result(null)
                .build());
  }
}
