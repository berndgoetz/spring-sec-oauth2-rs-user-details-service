package com.bgo.authdemo.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {

    public static final String SIGNING_KEY_CACHE = "signingKeys";
    public static final String AUTHZ_CACHE = "authz";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(SIGNING_KEY_CACHE, AUTHZ_CACHE);
    }

}