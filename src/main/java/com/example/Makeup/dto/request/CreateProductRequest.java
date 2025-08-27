package com.example.Makeup.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

  @NotNull(message = "Product name cannot be null")
  String name;

  @NotNull(message = "Description cannot be null")
  String description;

  @NotNull(message = "Size cannot be null")
  String size;

  @NotNull(message = "Price cannot be null")
  @Positive(message = "Price must be a positive number")
  double price;

  boolean status;

  List<MultipartFile> files;

  @Positive(message = "Category ID must be a positive number")
  int subCategoryId;

  @NotNull(message = "Quantity cannot be null")
  @Positive(message = "Quantity must be a positive number")
  int quantity;

  @NotNull(message = "Current quantity cannot be null")
  @Positive(message = "Current quantity must be a positive number")
  int currentQuantity;

  @NotNull(message = "Rental count cannot be null")
  @Positive(message = "Rental count must be a positive number")
  int rentalCount;
}
