package com.example.Makeup.repository;

import com.example.Makeup.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack,Integer> {
    @Query("SELECT f FROM FeedBack f JOIN FETCH f.user u WHERE f.rating >= :minRating")
    List<FeedBack> findByRatingGreaterThanEqual(@Param("minRating") int minRating);
}
