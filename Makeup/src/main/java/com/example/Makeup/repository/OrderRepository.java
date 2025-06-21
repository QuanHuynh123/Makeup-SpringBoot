package com.example.Makeup.repository;

import com.example.Makeup.entity.Order;
import com.example.Makeup.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    List<Order> findByStatus(OrderStatus status);


    Page<Order> findAll(Pageable pageable);


}
