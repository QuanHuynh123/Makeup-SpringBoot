package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubCategoryDTO {
    int id;
    String name;
    List<ProductDTO> products; // Nếu bạn muốn bao gồm danh sách sản phẩm
    int categoryId; // Chứa ID của danh mục cha
}
