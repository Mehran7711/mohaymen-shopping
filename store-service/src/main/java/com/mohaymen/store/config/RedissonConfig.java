package com.mohaymen.store.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Mehran
 * Created on: 7/12/26
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://redis:6379");
        return Redisson.create(config);
    }
}
