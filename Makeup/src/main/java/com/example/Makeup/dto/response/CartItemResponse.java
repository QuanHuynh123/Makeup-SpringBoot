package com.example.Makeup.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.util.UUID;

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
