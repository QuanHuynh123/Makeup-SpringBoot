package com.example.Makeup.repository;

import com.example.Makeup.entity.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

  @Query(value = "SELECT * FROM product p ORDER BY p.rental_count DESC", nativeQuery = true)
  List<Product> findTopRentalCount(Pageable pageable);

  @Query(value = "SELECT * FROM product p ORDER BY p.created_at DESC", nativeQuery = true)
  List<Product> findNewProducts(Pageable pageable);

  @Query(
      "SELECT p FROM Product p WHERE (:subCategoryId IS NULL OR p.subCategory.id = :subCategoryId) OR "
          + "(:search IS NULL OR LOWER(p.nameProduct) LIKE LOWER(CONCAT('%', :search, '%')))")
  Page<Product> searchProducts(
      @Param("subCategoryId") Integer subCategoryId, // Nullable Integer
      @Param("search") String search,
      Pageable pageable);

  Page<Product> findBySubCategoryIdAndIdNot(
      Integer subCategoryId, UUID excludedId, Pageable pageable);

  @Query("SELECT COUNT(p) FROM Product p WHERE p.subCategory.id = :subCategoryId")
  int countProductsBySubcategoryId(@Param("subCategoryId") Integer subCategoryId);

  @Query(value = "SELECT * FROM product ORDER BY RAND()", nativeQuery = true)
  List<Product> findRandomProducts(Pageable pageable);
}
