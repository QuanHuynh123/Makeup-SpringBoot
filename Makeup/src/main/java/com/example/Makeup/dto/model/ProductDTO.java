package com.example.Makeup.dto.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
    final UUID id;
    String nameProduct;
    String describe;
    String size;
    double price;
    boolean status;
    String image;
    int subCategoryId;
    int currentQuantity;
    int rentalCount;
    String firstImage;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
