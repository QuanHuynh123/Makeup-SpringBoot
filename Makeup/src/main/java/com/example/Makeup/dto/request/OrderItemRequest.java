package com.example.Makeup.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "Product ID cannot be null")
    UUID productId;

    @NotNull(message = "Order ID cannot be null")
    int quantity;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive number")
    double price;

    @NotNull(message = "Rental date cannot be null")
    LocalDate rentalDate;
}
