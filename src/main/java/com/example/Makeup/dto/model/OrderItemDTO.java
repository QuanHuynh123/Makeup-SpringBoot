package com.example.Makeup.dto.model;

import com.example.Makeup.enums.OrderItemStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class OrderItemDTO {
  final UUID id;
  int quantity;
  double price;
  OrderItemStatus status;
  LocalDateTime rentalDate;
  UUID orderId;
  UUID productId;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}
