package com.example.Makeup.repository;

import com.example.Makeup.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

    List<CartItem> findAllByCartId(Integer cartId);

    int countByCartId(Integer cartId);
    void deleteAllByCartId(Integer cartId);

}
