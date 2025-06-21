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
    USER_NOT_FOUND(400, "User not found!", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(400, "User not existed!", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTED(400, "User already existed!", HttpStatus.CONFLICT),
    PASSWORD_NOT_MATCH(400, "Password not match!", HttpStatus.BAD_REQUEST),

    CANT_FOUND(400, "Can't not found!", HttpStatus.NOT_FOUND),
    EXISTED(400, "Already existed!", HttpStatus.CONFLICT),
    PRODUCT_NOT_FOUND(400, "Product not found!", HttpStatus.NOT_FOUND),
    QUANTITY_NOT_ENOUGH(400, "Quantity not enough!", HttpStatus.BAD_REQUEST),
    PRODUCT_CONFLICT(400, "Product conflict!", HttpStatus.BAD_REQUEST),

    ORDER_NOT_FOUND(400, "Order not found!", HttpStatus.NOT_FOUND),
    ORDER_ITEM_NOT_FOUND(400, "Order item not found!", HttpStatus.NOT_FOUND),

    UNKNOWN_ERROR(400, "Unknown error code", HttpStatus.BAD_REQUEST),
    IS_EMPTY(400, "Empty!", HttpStatus.NOT_FOUND),

    STAFF_HAS_APPOINTMENTS(400, "Staff has appointments!", HttpStatus.BAD_REQUEST),
    STAFF_ALREADY_BOOKED(400, "Staff already booked!", HttpStatus.BAD_REQUEST),
;
    int code;
    String message;
    HttpStatusCode statusCode;
}
