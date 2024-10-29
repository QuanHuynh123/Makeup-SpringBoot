package com.example.Makeup.repository;

import com.example.Makeup.entity.ServiceMakeup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceMakeupRepository extends JpaRepository<ServiceMakeup,Integer> {

}
