package com.example.Makeup.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayRequest {
    private int amount;
    private String orderInfo;
    private String orderId;
}
