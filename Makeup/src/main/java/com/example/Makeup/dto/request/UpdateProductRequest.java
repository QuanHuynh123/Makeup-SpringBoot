package com.example.Makeup.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateProductRequest {

    @NotNull(message = "Product ID cannot be null")
    UUID id;

    @NotNull(message = "Product name cannot be null")
    String nameProduct;

    @NotNull(message = "Description cannot be null")
    String description;

    String size;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive number")
    double price;

    boolean status;

    String image;

    @NotNull(message = "Category ID cannot be null")
    int subCategoryId;

    List<MultipartFile> files;
}
