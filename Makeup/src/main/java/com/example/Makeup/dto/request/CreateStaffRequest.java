package com.example.Makeup.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStaffRequest {

    String nameStaff;
    String phone;
    String userName;
    String password;
    int role;
}
