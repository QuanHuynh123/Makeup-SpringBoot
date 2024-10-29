package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    int id; // ID của vai trò
    String nameRole; // Tên vai trò
}
