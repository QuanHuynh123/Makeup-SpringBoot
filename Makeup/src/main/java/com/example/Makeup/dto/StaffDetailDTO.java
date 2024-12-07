package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDetailDTO {
    int id;
    String nameStaff;
    String phone;
    String username; // Thêm trường username
    int role;
    String password;
}