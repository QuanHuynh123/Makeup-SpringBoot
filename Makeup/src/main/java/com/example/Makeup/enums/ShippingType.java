package com.example.Makeup.enums;

import lombok.Getter;

// This class is a placeholder for the ShippingType enum.
@Getter
public enum ShippingType {
  DELIVERY(1, "delivery"),
  PICKUP(2, "pickup");

  private final int id;
  private final String type;

  ShippingType(int id, String type) {
    this.id = id;
    this.type = type;
  }

  public static ShippingType fromId(int id) {
    for (ShippingType shippingType : values()) {
      if (shippingType.getId() == id) {
        return shippingType;
      }
    }
    throw new IllegalArgumentException("Invalid ShippingType id: " + id);
  }
}
