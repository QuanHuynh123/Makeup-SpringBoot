package com.example.Makeup.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class CartItemDTO {
    final UUID id;
    int quantity;
    double price;
    LocalDateTime rentalDate;
    UUID cartId;
    UUID productId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
