package com.example.Makeup.repository;

import com.example.Makeup.entity.Account;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
  Optional<Account> findByUserName(String userName);

  boolean existsByUserName(String userName);

  Optional<Account> findByUserNameIgnoreCase(String username);
}
