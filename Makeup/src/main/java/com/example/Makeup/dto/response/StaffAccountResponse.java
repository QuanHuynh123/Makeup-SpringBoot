package com.example.Makeup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffAccountResponse {

    private String id;
    private String nameStaff;
    private String phone;
    private String accountId;
    private String createdAt;
    private String updatedAt;
}

