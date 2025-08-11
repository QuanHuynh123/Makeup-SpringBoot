package com.example.Makeup.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
  private int code;
  private String message;
  private T result;

  public static <T> ApiResponse<T> success(String message, T result) {
    return ApiResponse.<T>builder().code(200).message(message).result(result).build();
  }

  public static <T> ApiResponse<T> error(int code, String message) {
    return ApiResponse.<T>builder().code(code).message(message).result(null).build();
  }
}
