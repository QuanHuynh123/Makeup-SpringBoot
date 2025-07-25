package com.example.Makeup.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
public class RedisStatusManager {

    @Getter
    private static volatile boolean isRedisAvailable = true;

    public static void setRedisAvailable(boolean available) {
        if (isRedisAvailable != available) {
            log.info("Redis status changed to: {}", available ? "AVAILABLE" : "UNAVAILABLE");
            isRedisAvailable = available;
        }
    }
}
