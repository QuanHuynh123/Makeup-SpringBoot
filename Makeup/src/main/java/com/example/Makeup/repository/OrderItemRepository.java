package com.example.Makeup.repository;

import com.example.Makeup.dto.response.OrderItemDetailResponse;
import com.example.Makeup.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    List<OrderItem> findAllByOrderId(UUID orderId);

    @Query("SELECT new com.example.Makeup.dto.response.OrderItemDetailResponse(" +
            "oi.id, oi.quantity, oi.price, oi.status, oi.rentalDate, oi.order.id, oi.product.id, " +
            "oi.product.nameProduct, oi.product.image, oi.createdAt, oi.updatedAt) " +
            "FROM OrderItem oi " +
            "WHERE oi.order.id = :orderId")
    List<OrderItemDetailResponse> findOrderItemsDetailByOrderId(@Param("orderId") UUID orderId);
}