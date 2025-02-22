package com.example.Makeup.repository;

import com.example.Makeup.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  OrderItemRepository extends JpaRepository<OrderItem,Integer> {

    List<OrderItem> findAllByOrderId(Integer orderId);
}
