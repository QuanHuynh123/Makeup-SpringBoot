package com.example.Makeup.service.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisHealthCheckService {

    private final RedisConnectionFactory connectionFactory;
    private boolean redisAvailable = true; // lưu trạng thái hiện tại

    public void checkConnection() {
        try (RedisConnection connection = connectionFactory.getConnection()) {
            connection.ping();
            if (!redisAvailable) {
                log.info("Redis connection restored");
                redisAvailable = true;
            }
        } catch (RedisConnectionFailureException e) {
            if (redisAvailable) {
                log.warn("⚠️ Redis connection lost: {}", e.getMessage());
                redisAvailable = false;
            }
        } catch (Exception e) {
            log.error("Redis health check failed: {}", e.getMessage());
            redisAvailable = false;
        }
    }

    public boolean isRedisAvailable() {
        return redisAvailable;
    }
}