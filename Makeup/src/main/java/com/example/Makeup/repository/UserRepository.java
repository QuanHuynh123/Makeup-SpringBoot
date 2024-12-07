package com.example.Makeup.repository;

import com.example.Makeup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByFullName(String fullName);
    @Query("SELECT u FROM User u WHERE u.phone = :phone AND u.account IS NOT NULL")
    User findByPhoneAndAccountNotNull(@Param("phone") String phone);

    User findByAccountId(int accountId);
    Optional<User> findByEmailAndPhone(String email, String phone);

}
