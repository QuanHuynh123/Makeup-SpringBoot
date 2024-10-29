package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    int id;
    String namePaymentMethod; // Tên phương thức thanh toán
    boolean status; // Trạng thái của phương thức thanh toán
}
