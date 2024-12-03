package com.example.Makeup.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    int id;
    int quantity;
    double price;
    int cartId;
    int productId;
    String productName;
    String imageFirst;
    Date useDate;
    int cartItemId;
}
