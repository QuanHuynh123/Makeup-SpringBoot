package com.example.Makeup.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class UpdateProductRequest {

    String nameProduct;
    String description;
    String size;
    double price;
    boolean status;
    String image;
    int subCategoryId;

    List<MultipartFile> files;
}
