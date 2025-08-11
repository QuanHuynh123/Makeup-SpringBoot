package com.example.Makeup.repository;

import com.example.Makeup.dto.response.CartItemResponse;
import com.example.Makeup.entity.CartItem;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

  List<CartItem> findAllByCartId(UUID cartId);

  int countByCartId(UUID cartId);

  int deleteAllByCartId(UUID cartId);

  @Query(
      "SELECT new com.example.Makeup.dto.response.CartItemResponse("
          + "ci.id, ci.quantity, ci.price, ci.rentalDate, ci.cart.id, ci.product.id, "
          + "p.nameProduct, p.image, ci.createdAt, ci.updatedAt) "
          + "FROM CartItem ci JOIN ci.product p WHERE ci.cart.id = :cartId")
  List<CartItemResponse> findAllCartItem(@Param("cartId") UUID cartId);
}
