package com.example.Makeup.dto.response;

import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
