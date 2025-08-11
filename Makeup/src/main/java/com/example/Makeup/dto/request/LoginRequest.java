package com.example.Makeup.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

  @NotEmpty(message = "Username is required")
  private String username;

  @NotEmpty(message = "Password is required")
  private String password;
}
