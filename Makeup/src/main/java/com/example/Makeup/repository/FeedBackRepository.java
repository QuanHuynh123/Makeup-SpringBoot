package com.example.Makeup.repository;

import com.example.Makeup.entity.FeedBack;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, UUID> {

  @Query("SELECT f FROM FeedBack f LEFT JOIN FETCH f.user u WHERE f.rating >= :minRating")
  List<FeedBack> findByRatingGreaterThanEqual(@Param("minRating") int minRating);
}
