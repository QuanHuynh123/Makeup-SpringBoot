package com.example.Makeup.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryType {
  AMIME(1, "Anime"),
  MOVIE(2, "Movie"),
  GAME(3, "Game"),
  FESTIVAL(4, "Festival");

  private final int id;
  private final String name;

  public static CategoryType fromId(int id) {
    for (CategoryType categoryType : values()) {
      if (categoryType.getId() == id) {
        return categoryType;
      }
    }
    throw new IllegalArgumentException("Invalid CategoryType id: " + id);
  }
}
