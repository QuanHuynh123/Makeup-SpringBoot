package com.example.Makeup.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum OrderStatus {
    PENDING(0, "Order is pending"),
    IN_PROGRESS(1, "Order is in progress"),
    HAS_PICKED_UP(1, "Order has been picked up"),
    COMPLETED(2, "Order has been completed"),
    CANCELLED(3, "Order has been cancelled");

    private final int id;
    private final String description;

    OrderStatus(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static OrderStatus fromId(int id) {
        for (OrderStatus status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus id: " + id);
    }

}