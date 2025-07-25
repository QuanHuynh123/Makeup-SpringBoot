package com.example.Makeup.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class OrderItemRequest {

    UUID productId;
    int quantity;
    double price;
    LocalDate rentalDate;
}
