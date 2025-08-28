package com.example.Makeup.service;

import com.example.Makeup.dto.model.CartDTO;

import java.util.UUID;

public interface ICartService {

  CartDTO getCart(UUID userId);

  void createCart(UUID accountId);
}
