package com.example.Makeup.dto.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
  int id;
  String name;
  List<SubCategoryDTO> subCategories;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}
