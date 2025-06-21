package com.example.Makeup.repository;

import com.example.Makeup.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart,UUID> {

    Optional<Cart> findByUserId(UUID userId);

    @Query("SELECT COUNT(c) > 0 FROM Cart c WHERE c.id = :cartId AND c.user.id = :userId")
    boolean existsByIdAndUserId(@Param("cartId") UUID cartId, @Param("userId") UUID userId);

}
