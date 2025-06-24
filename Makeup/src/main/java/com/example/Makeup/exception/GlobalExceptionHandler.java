//package com.example.Makeup.exception;
//
//import com.example.Makeup.dto.response.common.ApiResponse;
//import com.example.Makeup.enums.ErrorCode;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(value = RuntimeException.class)
//    public ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException e) {
//        log.error(e.getMessage());
//        ErrorCode errorCode = ErrorCode.UNKNOWN_ERROR;
//        ApiResponse apiResponse = ApiResponse.builder()
//                .code(errorCode.getStatusCode().value())
//                .message(ErrorCode.UNKNOWN_ERROR.getMessage())
//                .build();
//        return ResponseEntity.status(ErrorCode.UNKNOWN_ERROR.getStatusCode()).body(apiResponse);
//    }
//
//    @ExceptionHandler(value = AppException.class)
//    ResponseEntity<ApiResponse> handlingAppException(AppException e) {
//        ErrorCode errorCode = e.getErrorCode();
//        ApiResponse response = ApiResponse.builder()
//                .code(errorCode.getStatusCode().value())
//                .message(errorCode.getMessage())
//                .build();
//        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
//        String msg = ex.getBindingResult().getFieldError().getDefaultMessage();
//        return ResponseEntity.badRequest().body(
//                ApiResponse.builder()
//                        .code(HttpStatus.BAD_REQUEST.value())
//                        .message(msg)
//                        .result(null)
//                        .build()
//        );
//    }
//
//
//}
