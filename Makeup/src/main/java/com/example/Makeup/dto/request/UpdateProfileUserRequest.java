package com.example.Makeup.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileUserRequest {

    @NotNull(message = "Name is required")
    String name;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    String email;

    @NotEmpty(message = "Phone number is required")
    String phone;

    @NotEmpty(message = "Address is required")
    String address;
}
