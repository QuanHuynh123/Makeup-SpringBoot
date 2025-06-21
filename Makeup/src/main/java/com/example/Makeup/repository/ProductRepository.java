package com.example.Makeup.repository;

import com.example.Makeup.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query(value = "SELECT * FROM makeup.product p ORDER BY p.rental_count DESC", nativeQuery = true)
    List<Product> findTopRentalCount(PageRequest pageable);


    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC")
    List<Product> findTopCreatedAt(PageRequest pageable);

    Page<Product> findBySubCategoryId(Integer subCategoryId, Pageable pageable); // Thêm Pageable

    @Query("SELECT COUNT(p) FROM Product p WHERE p.subCategory.id = :subCategoryId")
    int countProductsBySubcategoryId(@Param("subCategoryId") Integer subCategoryId);

}
