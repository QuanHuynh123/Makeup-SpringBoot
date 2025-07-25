package com.example.Makeup.dto.response;

import com.example.Makeup.enums.OrderItemStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDetailResponse {
    UUID id;
    int quantity;
    double price;
    OrderItemStatus status;
    LocalDateTime rentalDate;
    UUID orderId;
    UUID productId;
    String productName;
    String firstImageUrl;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
