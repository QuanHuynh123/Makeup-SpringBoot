package com.example.Makeup.listener;

import com.example.Makeup.entity.Product;
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

    // Trick to inject static beans into listeners (since Listeners are usually not managed directly by Spring)
    @Autowired
    public void init(StringRedisTemplate redisTemplate) {
        ProductEntityListener.redisTemplate = redisTemplate;
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
        String key = "product:" + product.getId();
        redisTemplate.opsForHash().put(key, "name", product.getNameProduct());
        redisTemplate.opsForHash().put(key, "price", String.valueOf(product.getPrice()));
        redisTemplate.opsForHash().put(key, "quantity", String.valueOf(product.getCurrentQuantity()));

        // Invalidate list caches
        redisTemplate.delete("products-hot");
        redisTemplate.delete("products-new");
        redisTemplate.delete("products-customer-show");
    }

    private void deleteFromRedis(Product product) {
        String key = "product:" + product.getId();
        redisTemplate.delete(key);

        // Invalidate list caches
        redisTemplate.delete("products-hot");
        redisTemplate.delete("products-new");
        redisTemplate.delete("products-customer-show");
    }
}
