package com.example.Makeup.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStaffRequest {

    @NotEmpty(message = "Name is required")
    String nameStaff;

    @NotEmpty(message = "Phone number is required")
    String phone;

    @NotEmpty(message = "Username is required")
    String userName;

    @NotEmpty(message = "Password is required")
    String password;

    @NotNull(message = "Role is required")
    int role;
}
