package com.example.Makeup.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    String name;
    String description;
    String size;
    double price;
    boolean status;
    List<MultipartFile> files;
    int subCategoryId;
    int quantity;
    int currentQuantity;
    int rentalCount;
}
