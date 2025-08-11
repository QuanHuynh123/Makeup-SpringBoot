package com.example.Makeup.repository;

import com.example.Makeup.entity.TypeMakeup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeMakeupRepository extends JpaRepository<TypeMakeup, Integer> {}
