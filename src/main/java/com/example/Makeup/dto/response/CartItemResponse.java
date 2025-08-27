package com.example.Makeup.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {

  UUID id;
  int quantity;
  double price;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDateTime rentalDate;

  UUID cartId;
  UUID productId;
  String productName;
  String firstImage;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}
