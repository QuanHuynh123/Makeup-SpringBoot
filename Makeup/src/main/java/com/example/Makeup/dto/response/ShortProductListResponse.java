package com.example.Makeup.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortProductListResponse {
    UUID id;
    String nameProduct;
    double price;
    String firstImage;
}
