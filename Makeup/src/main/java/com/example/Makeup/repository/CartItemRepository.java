package com.example.Makeup.repository;

import com.example.Makeup.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,UUID> {

    List<CartItem> findAllByCartId(UUID cartId);

    int countByCartId(UUID cartId);
    int deleteAllByCartId(UUID cartId);

}
