//package com.mohaymen.store.config;
//
//import com.mohaymen.store.model.Product;
//import org.ehcache.config.builders.CacheConfigurationBuilder;
//import org.ehcache.config.builders.ExpiryPolicyBuilder;
//import org.ehcache.config.builders.ResourcePoolsBuilder;
//import org.ehcache.jsr107.Eh107Configuration;
//import org.hibernate.cache.jcache.ConfigSettings;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
//import org.springframework.boot.info.BuildProperties;
//import org.springframework.boot.info.GitProperties;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import javax.cache.CacheManager;
//
//import java.time.Duration;
//
//@Configuration
//@EnableCaching
//public class CacheConfiguration {
//
//    private GitProperties gitProperties;
//    private BuildProperties buildProperties;
//    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;
//
//    public CacheConfiguration(StoreProperties storeProperties) {
//
//        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
//                CacheConfigurationBuilder.newCacheConfigurationBuilder(
//                                Object.class,
//                                Object.class,
//                                ResourcePoolsBuilder.heap(storeProperties.getMaxEntries())
//                        )
//                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(storeProperties.getTimeToLiveSeconds())))
//                        .build()
//        );
//    }
//
//    @Bean
//    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
//        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
//    }
//
//    @Bean
//    public CacheManager cacheManager() {
//        CachingProvider provider = Caching.getCachingProvider();
//        CacheManager cacheManager = provider.getCacheManager();
//
//        MutableConfiguration<Object, Object> config =
//                new MutableConfiguration<>()
//                        .setStoreByValue(false)
//                        .setStatisticsEnabled(true);
//
//        cacheManager.createCache(Product.class.getName(), config);
//
//        return cacheManager;
//    }
//
//
//    private void createCache(javax.cache.CacheManager cm, String cacheName) {
//        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
//        if (cache != null) {
//            cache.clear();
//        } else {
//            cm.createCache(cacheName, jcacheConfiguration);
//        }
//    }
//
//    @Autowired(required = false)
//    public void setGitProperties(GitProperties gitProperties) {
//        this.gitProperties = gitProperties;
//    }
//
//    @Autowired(required = false)
//    public void setBuildProperties(BuildProperties buildProperties) {
//        this.buildProperties = buildProperties;
//    }
//
//    @Bean
//    public KeyGenerator keyGenerator() {
//        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
//    }
//}
