package com.example.Makeup.listener;

import com.example.Makeup.entity.Product;
import com.example.Makeup.service.common.CacheKeys;
import com.example.Makeup.service.common.RedisCacheHelper;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductEntityListener {

    private static StringRedisTemplate redisTemplate;
    private static RedisCacheHelper redisCacheHelper;

    // Trick to inject static beans into listeners (since Listeners are usually not managed directly by Spring)
    @Autowired
    public void init(StringRedisTemplate redisTemplate, RedisCacheHelper redisCacheHelper) {
        ProductEntityListener.redisTemplate = redisTemplate;
        ProductEntityListener.redisCacheHelper = redisCacheHelper;
    }

    @PostPersist
    public void afterInsert(Product product) {
        log.info("Product created: {}", product.getId());
        updateRedis(product);
    }

    @PostUpdate
    public void afterUpdate(Product product) {
        log.info("Product updated: {}", product.getId());
        updateRedis(product);
    }

    @PostRemove
    public void afterDelete(Product product) {
        log.info("Product deleted: {}", product.getId());
        deleteFromRedis(product);
    }

    private void updateRedis(Product product) {
        String key = CacheKeys.productById(product.getId());
        try {
            redisTemplate.opsForHash().put(key, "name", product.getNameProduct());
            redisTemplate.opsForHash().put(key, "price", String.valueOf(product.getPrice()));
            redisTemplate.opsForHash().put(key, "quantity", String.valueOf(product.getCurrentQuantity()));
        } catch (Exception e) {
            log.warn("Redis product cache update skipped for key {}: {}", key, e.getMessage());
        }

        redisCacheHelper.evict(
                CacheKeys.hotProducts(),
                CacheKeys.newProducts(),
                CacheKeys.customerShowProducts());
    }

    private void deleteFromRedis(Product product) {
        String key = CacheKeys.productById(product.getId());
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.warn("Redis product cache delete skipped for key {}: {}", key, e.getMessage());
        }

        redisCacheHelper.evict(
                CacheKeys.hotProducts(),
                CacheKeys.newProducts(),
                CacheKeys.customerShowProducts());
    }
}
