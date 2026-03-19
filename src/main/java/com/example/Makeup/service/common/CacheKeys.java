package com.example.Makeup.service.common;

import java.util.UUID;

public final class CacheKeys {

  private static final String CACHE_PREFIX = "cache:";

  private CacheKeys() {}

  public static String hotProducts() {
    return CACHE_PREFIX + "products:hot";
  }

  public static String newProducts() {
    return CACHE_PREFIX + "products:new";
  }

  public static String customerShowProducts() {
    return CACHE_PREFIX + "products:customer-show";
  }

  public static String productById(UUID productId) {
    return CACHE_PREFIX + "products:item:" + productId;
  }

  public static String categories() {
    return CACHE_PREFIX + "categories:all";
  }

  public static String goodFeedbacks() {
    return CACHE_PREFIX + "feedbacks:good";
  }

  public static String staffs() {
    return CACHE_PREFIX + "staffs:all";
  }

  public static String typeMakeups() {
    return CACHE_PREFIX + "type-makeups:all";
  }
}
