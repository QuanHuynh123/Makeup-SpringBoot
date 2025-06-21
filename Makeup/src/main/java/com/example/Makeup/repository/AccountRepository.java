package com.example.Makeup.repository;

import com.example.Makeup.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository  extends JpaRepository<Account, UUID> {
    Optional<Account> findByUserName(String userName);

    boolean existsByUserName(String userName);

    Optional<Account> findByUserNameIgnoreCase(String username);
}
