package com.example.Makeup.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDTO {
    final UUID id;
    double totalPrice;
    int totalQuantity;
    UUID userId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
