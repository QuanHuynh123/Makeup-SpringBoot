package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    int id; // ID của nhân viên
    String nameStaff; // Tên nhân viên
    String phone; // Số điện thoại
}
