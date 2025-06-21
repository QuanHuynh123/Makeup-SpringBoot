package com.example.Makeup.dto.model;

import com.example.Makeup.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {
    final UUID id;
    double totalPrice;
    int totalQuantity;
    LocalDateTime orderDate;
    LocalDateTime pickupDate;
    LocalDateTime returnDate;
    OrderStatus status;
    UUID userId;
    int paymentId;
    boolean returnStatus;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
