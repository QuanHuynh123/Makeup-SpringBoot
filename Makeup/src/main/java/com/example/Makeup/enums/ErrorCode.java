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

    // ===== AUTH & TOKEN =====
    AUTH_REFRESH_TOKEN_NOT_FOUND("Refresh token not found!", HttpStatus.NOT_FOUND),
    AUTH_REFRESH_TOKEN_EXPIRED("Refresh token expired!", HttpStatus.UNAUTHORIZED),

    // ===== USER =====
    USER_NOT_FOUND("User not found!", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED("User not existed!", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTED("User already existed!", HttpStatus.CONFLICT),
    USER_PASSWORD_NOT_MATCH("Password not match!", HttpStatus.BAD_REQUEST),

    // ===== PRODUCT =====
    PRODUCT_NOT_FOUND("Product not found!", HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_EXISTED("Product already existed!", HttpStatus.CONFLICT),
    PRODUCT_CONFLICT("Product conflict!", HttpStatus.CONFLICT),
    PRODUCT_QUANTITY_NOT_ENOUGH("Quantity not enough!", HttpStatus.BAD_REQUEST),
    PRODUCT_IS_EMPTY("Product is empty!", HttpStatus.NO_CONTENT),

    // ===== CART =====
    CART_NOT_FOUND("Cart not found!", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND("Cart item not found!", HttpStatus.NOT_FOUND),
    CART_IS_EMPTY("Cart is empty!", HttpStatus.NO_CONTENT),
    CART_ITEM_DELETE_FAILED("Delete cart item failed!", HttpStatus.BAD_REQUEST),

    // ===== ORDER =====
    ORDER_NOT_FOUND("Order not found!", HttpStatus.NOT_FOUND),
    ORDER_ITEM_NOT_FOUND("Order item not found!", HttpStatus.NOT_FOUND),
    ORDER_IS_EMPTY("Order is empty!", HttpStatus.NO_CONTENT),

    // ===== STAFF =====
    STAFF_ALREADY_BOOKED("Staff already booked!", HttpStatus.BAD_REQUEST),
    STAFF_HAS_APPOINTMENTS("Staff has appointments!", HttpStatus.BAD_REQUEST),
    STAFF_NOT_FOUND("Staff not found!", HttpStatus.NOT_FOUND),
    STAFF_IS_EMPTY("Staff is empty!", HttpStatus.NO_CONTENT),

    // ===== APPOINTMENT =====
    APPOINTMENT_NOT_FOUND("Appointment not found!", HttpStatus.NOT_FOUND),
    APPOINTMENT_ALREADY_EXISTED("Appointment already existed!", HttpStatus.CONFLICT),
    APPOINTMENT_IS_EMPTY("Appointment is empty!", HttpStatus.NO_CONTENT),
    APPOINTMENT_CONFLICT("Appointment conflict!", HttpStatus.CONFLICT),
    APPOINTMENT_NOT_CONFIRMED("Appointment not confirmed!", HttpStatus.BAD_REQUEST),

    // ===== COMMON =====
    COMMON_RESOURCE_NOT_FOUND("Can't not found!", HttpStatus.NOT_FOUND),
    COMMON_RESOURCE_ALREADY_EXISTED("Already existed!", HttpStatus.CONFLICT),
    COMMON_IS_EMPTY("Empty!", HttpStatus.NO_CONTENT),

    // ===== DEFAULT / UNKNOWN =====
    UNKNOWN_ERROR("Unknown error code", HttpStatus.INTERNAL_SERVER_ERROR);
;
    String message;
    HttpStatusCode statusCode;
}
