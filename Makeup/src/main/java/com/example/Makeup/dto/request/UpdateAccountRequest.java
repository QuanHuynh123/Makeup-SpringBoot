package com.example.Makeup.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountRequest {

    private String passWord;
    private int roleId;
}
