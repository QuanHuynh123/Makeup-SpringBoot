package com.example.Makeup.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    int id;
    String name;
    List<SubCategoryDTO> subCategories;
    LocalDateTime createDate;
    LocalDateTime updateDate;
}
