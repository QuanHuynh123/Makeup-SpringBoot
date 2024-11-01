package com.example.Makeup.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1001, "User not found!", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTED(1002, "User already existed!", HttpStatus.CONFLICT),
    CANT_FOUND(1003, "Can't not found!", HttpStatus.NOT_FOUND),
    EXISTED(1004, "Already existed!", HttpStatus.CONFLICT),
    PRODUCT_NOT_FOUND(2001, "Product not found!", HttpStatus.NOT_FOUND),

    ORDER_NOT_FOUND(4001, "Order not found!", HttpStatus.NOT_FOUND),

    ORDER_ITEM_NOT_FOUND(5001, "Order item not found!", HttpStatus.NOT_FOUND),

    UNKNOWN_ERROR(9999, "Unknown error code", HttpStatus.BAD_REQUEST),
    ;

    int code;
    String message;
    HttpStatusCode statusCode;
}
