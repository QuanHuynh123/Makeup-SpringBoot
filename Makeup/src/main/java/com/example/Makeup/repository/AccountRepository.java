package com.example.Makeup.repository;

import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository  extends JpaRepository<Account,Integer> {
    Optional<Account> findByUserName(String userName);

    boolean existsByUserName(String userName);

}
