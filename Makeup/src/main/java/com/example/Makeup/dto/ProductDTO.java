package com.example.Makeup.dto;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    int id;
    String nameProduct;
    String describe;
    String size;
    double price;
    boolean status;
    String image;
    int subCategoryId;
    int rentalCount;
    Date createdAt;
    String firstImage;
    String subCategoryName;
}
