package com.example.Makeup.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JWTProvider {

  @Value("${jwt.secret}")
  private String secretkey;

  @Value("${jwt.expiration}")
  private long expirationMs;

  public String generateToken(String username, Integer role, UUID accountId) {
    log.info("Generating JWT token for user: {}", username);
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", role);
    claims.put("accountId", accountId.toString());
    return Jwts.builder()
        .claims()
        .add(claims)
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expirationMs))
        .and()
        .signWith(getKey())
        .compact();
  }

  private SecretKey getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretkey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
  }

  public Integer extractRole(String token) {
    return extractClaim(token, claims -> (Integer) claims.get("role"));
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public boolean isTokenValid(String token) {
    if (token == null) return false;
    try {
      extractAllClaims(token);
      return !isTokenExpired(token);
    } catch (Exception ex) {
      log.error("Invalid token: {}", ex.getMessage());
      return false;
    }
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public String extractUserNameAllowExpired(String token) {
    return extractAllClaimsAllowExpired(token).getSubject();
  }

  public UUID extractAccountIdAllowExpired(String token) {
    String accountIdStr = extractAllClaimsAllowExpired(token).get("accountId", String.class);
    return UUID.fromString(accountIdStr);
  }

  public Integer extractRoleAllowExpired(String token) {
    return extractAllClaimsAllowExpired(token).get("role", Integer.class);
  }

  private Claims extractAllClaimsAllowExpired(String token) {
    try {
      return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
