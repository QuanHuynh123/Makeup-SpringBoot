package com.example.Makeup.repository;

import com.example.Makeup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByFullName(String fullName);
    User findByPhone(String phone);


}
