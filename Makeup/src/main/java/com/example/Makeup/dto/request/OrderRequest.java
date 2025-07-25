package com.example.Makeup.dto.request;

import com.example.Makeup.enums.ShippingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequest {

    private String email;
    private String firstName;
    private String phoneNumber;
    private String message;
    String address;
    int typeShipping;
    private int paymentMethod;
    private int quantity;
    private double totalPrice;

    List<OrderItemRequest> orderItems;
}
