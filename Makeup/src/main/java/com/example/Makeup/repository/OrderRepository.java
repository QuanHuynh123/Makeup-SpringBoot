package com.example.Makeup.repository;

import com.example.Makeup.dto.response.OrdersAdminResponse;
import com.example.Makeup.entity.Order;
import com.example.Makeup.enums.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT MONTH(o.orderDate), COUNT(o) FROM Order o WHERE YEAR(o.orderDate) = :year GROUP BY MONTH(o.orderDate)")
    List<Object[]> findOrdersCountByMonth(@Param("year") int year);

    @Query("SELECT DATE(o.orderDate), COUNT(o) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate GROUP BY DATE(o.orderDate)")
    List<Object[]> findOrdersCountByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT DAY(o.orderDate), COUNT(o) FROM Order o WHERE MONTH(o.orderDate) = :month AND YEAR(o.orderDate) = :year GROUP BY DAY(o.orderDate)")
    List<Object[]> findOrdersCountByCurrentMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT DATE(o.orderDate), COUNT(o) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate GROUP BY DATE(o.orderDate)")
    List<Object[]> findOrdersCountByCustomRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    List<Order> findByUserId(UUID userId);

    @Query("SELECT new com.example.Makeup.dto.response.OrdersAdminResponse(" +
            "o.id, o.totalPrice, o.totalQuantity, o.orderDate, o.pickupDate, o.returnDate, o.status, " +
            "u.id, u.fullName, u.phone, p.id, p.namePaymentMethod, o.createdAt, o.updatedAt) " +
            "FROM Order o " +
            "JOIN o.user u " +
            "JOIN o.payment p " +
            "WHERE (:status IS NULL OR o.status = :status)")
    Page<OrdersAdminResponse> findAllOrders(@Param("status") OrderStatus status, Pageable pageable);


    Page<Order> findAll(Pageable pageable);

    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    int updateOrderStatus(@Param("orderId") UUID orderId, @Param("status") OrderStatus status);

    @EntityGraph(attributePaths = "orderItems") // name attribute in Order entity
    Optional<Order> findWithOrderItemsById(UUID id);

    @Modifying
    @Query("DELETE FROM Order o WHERE o.status = 'PAYMENT_NOT_COMPLETED' AND o.createAt < :expiredTime")
    int clearOrderPaymentExpired(@Param("expiredTime") LocalDateTime expiredTime);


}
