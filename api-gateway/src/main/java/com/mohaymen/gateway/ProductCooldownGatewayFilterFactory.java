package com.mohaymen.gateway;

import java.util.List;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
class ProductCooldownGatewayFilterFactory extends AbstractGatewayFilterFactory<ProductCooldownGatewayFilterFactory.Config> {

    private static final DefaultRedisScript<List> SCRIPT = new DefaultRedisScript<>();

    static {
        SCRIPT.setResultType(List.class);
        SCRIPT.setScriptText("""
                local key = KEYS[1]
                local now = tonumber(ARGV[1])
                local window = tonumber(ARGV[2])
                local limit = tonumber(ARGV[3])
                local member = ARGV[4]
                redis.call('ZREMRANGEBYSCORE', key, '-inf', now - window)
                local count = redis.call('ZCARD', key)
                if count >= limit then
                  local oldest = redis.call('ZRANGE', key, 0, 0, 'WITHSCORES')
                  local retryMs = window
                  if oldest[2] ~= nil then
                    retryMs = tonumber(oldest[2]) + window - now
                  end
                  if retryMs < 1 then
                    retryMs = 1
                  end
                  return {0, retryMs}
                end
                redis.call('ZADD', key, now, member)
                redis.call('PEXPIRE', key, window)
                return {1, 0}
                """);
    }

    private final ReactiveStringRedisTemplate redis;
    private final KeyResolver userOrIpKeyResolver;

    ProductCooldownGatewayFilterFactory(ReactiveStringRedisTemplate redis, KeyResolver userOrIpKeyResolver) {
        super(Config.class);
        this.redis = redis;
        this.userOrIpKeyResolver = userOrIpKeyResolver;
    }

    @Override
    public GatewayFilter apply(Config config) {
        long windowMillis = config.getWindowSeconds() * 1000L;
        return (exchange, chain) -> userOrIpKeyResolver.resolve(exchange).flatMap(key -> {
            long now = System.currentTimeMillis();
            String redisKey = "rate:products:window:" + key;
            return redis.execute(
                            SCRIPT,
                            List.of(redisKey),
                            List.of(
                                    Long.toString(now),
                                    Long.toString(windowMillis),
                                    Integer.toString(config.getLimit()),
                                    now + "-" + System.nanoTime()))
                    .next()
                    .flatMap(result -> {
                        boolean allowed = asLong(result.get(0)) == 1L;
                        if (allowed) {
                            return chain.filter(exchange);
                        }
                        long retrySeconds = Math.max(1L, (asLong(result.get(1)) + 999L) / 1000L);
                        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        exchange.getResponse().getHeaders().set(HttpHeaders.RETRY_AFTER, Long.toString(retrySeconds));
                        return exchange.getResponse().setComplete();
                    });
        });
    }

    private static long asLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        return Long.parseLong(String.valueOf(value));
    }

    static class Config {
        private int limit = 11;
        private int windowSeconds = 10;

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getWindowSeconds() {
            return windowSeconds;
        }

        public void setWindowSeconds(int windowSeconds) {
            this.windowSeconds = windowSeconds;
        }
    }
}
