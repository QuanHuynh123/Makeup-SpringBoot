package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    int id; // ID của sản phẩm
    String nameProduct; // Tên sản phẩm
    String describe; // Mô tả sản phẩm
    String size; // Kích thước sản phẩm
    double price; // Giá sản phẩm
    boolean status; // Trạng thái của sản phẩm
    String image; // URL hình ảnh của sản phẩm
    int categoryId; // ID của danh mục sản phẩm
}
