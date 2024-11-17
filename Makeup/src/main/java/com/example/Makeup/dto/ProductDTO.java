package com.example.Makeup.dto;

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
    List<String> imageList;
    int subCategoryId;
    
    public String getFirstImage() {
        return (imageList != null && !imageList.isEmpty()) ? imageList.get(0) : null;
    }
}
