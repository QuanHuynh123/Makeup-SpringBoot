package com.example.Makeup.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {

  @NotEmpty(message = "UserName is required")
  private String userName;

  @NotEmpty(message = "Password is required")
  private String passWord;
}
