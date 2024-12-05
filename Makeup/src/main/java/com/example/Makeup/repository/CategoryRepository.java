package com.example.Makeup.repository;

import com.example.Makeup.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("SELECT c FROM Category c JOIN FETCH c.subCategories")
    List<Category> findAllWithSubCategories();

}
