package com.example.Makeup.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemRequest {

    @NotNull(message = "Cart item ID is required")
    UUID cartItemId;

    @NotNull(message = "Product ID is required")
    UUID productId;

    @NotNull(message = "Quantity is required")
    Integer quantity;

    @NotNull(message = "Rental date is required")
    LocalDateTime rentalDate;
}
