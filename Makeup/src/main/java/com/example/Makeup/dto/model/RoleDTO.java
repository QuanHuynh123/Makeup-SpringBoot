package com.example.Makeup.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    int id;
    String nameRole;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
