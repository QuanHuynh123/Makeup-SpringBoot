package com.example.Makeup.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountRequest {

    @NotEmpty(message = "PassWord is required")
    private String passWord;

    @NotNull(message = "Role ID is required")
    private int roleId;
}
