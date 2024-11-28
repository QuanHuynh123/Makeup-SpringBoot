package com.example.Makeup.repository;

import com.example.Makeup.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query(value = "SELECT * FROM makeup.product p ORDER BY p.rental_count DESC", nativeQuery = true)
    List<Product> findTopRentalCount(PageRequest pageable);


    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC")
    List<Product> findTopCreatedAt(PageRequest pageable);
}
