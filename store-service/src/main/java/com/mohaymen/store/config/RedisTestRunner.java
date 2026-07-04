package com.mohaymen.store.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTestRunner implements CommandLineRunner {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisTestRunner(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            // Test Redis connection
            redisTemplate.opsForValue().set("test-connection", "Hello Redis!");
            String value = (String) redisTemplate.opsForValue().get("test-connection");
            System.out.println("✅ Redis connection successful! Value: " + value);
            redisTemplate.delete("test-connection");

            // Check Redis info
            System.out.println("✅ Redis is up and running!");
        } catch (Exception e) {
            System.err.println("❌ Redis connection failed!!: " + e.getMessage());
        }
    }
}
