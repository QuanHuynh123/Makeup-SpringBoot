package com.example.Makeup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEmailMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String to;
    private String orderId;
    private String receiverName;
}