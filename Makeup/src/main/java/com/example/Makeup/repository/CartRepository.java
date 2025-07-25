package com.example.Makeup.repository;

import com.example.Makeup.entity.Cart;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Cart c WHERE c.id = :id")
    Optional<Cart> findByIdForUpdate(@Param("id") UUID cartId);
}
