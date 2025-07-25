package com.example.Makeup.repository;

import com.example.Makeup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByFullName(String fullName);

    Optional<User> findByAccount_userName(String userName);

    Optional<User> findByAccount_Id(UUID id);

    Optional<User> findByAccountId(UUID accountId);

    Optional<User> findByEmailAndPhone(String email, String phone);

}
