package com.example.Makeup.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubCategoryDTO {
    int id;
    String name;
    int categoryId;
    boolean status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
