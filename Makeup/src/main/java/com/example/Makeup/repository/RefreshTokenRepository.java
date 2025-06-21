package com.example.Makeup.repository;

import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {


    @Query("""
        SELECT rt FROM RefreshToken rt
        WHERE rt.account.id = :accountId
          AND rt.revoked = false
          AND rt.expiryDate > CURRENT_TIMESTAMP
        ORDER BY rt.expiryDate DESC
        """)
    Optional<RefreshToken> findLatestValidTokenByAccount(@Param("accountId") UUID accountId);


    @Modifying
    @Query("UPDATE RefreshToken t SET t.revoked = true WHERE t.account.id = :accountId AND t.revoked = false")
    void revokeAllByAccount(@Param("accountId") UUID accountId);

}
