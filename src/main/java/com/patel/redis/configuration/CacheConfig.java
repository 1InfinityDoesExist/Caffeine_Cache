package com.patel.redis.configuration;

import java.util.concurrent.TimeUnit;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;

/*
 * Can also be configured via application.properties
 */
@Configuration
public class CacheConfig {

	/**
	 * initialCapacity maximumSize maximumWeight expireAfterAccess expireAfterWrite
	 * refreshAfterWrite weakKeys weakValues softValues recordStats
	 */

	@Bean
	public CacheManager cacheManager() {
		String specAsString = "initialCapacity=100, maximumSize=500, expireAfterAccess=5m, recordStats";
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("profileDetails");
		caffeineCacheManager.setAllowNullValues(false);
		/*
		 * Three ways to setSpecification
		 */
		// caffeineCacheManager.setCaffeineSpec(caffeineSpec());
		// caffeineCacheManager.setCacheSpecification(specAsString);
		caffeineCacheManager.setCaffeine(caffeineCacheBuilder());
		return caffeineCacheManager;
	}

	CaffeineSpec caffeineSpec() {
		return CaffeineSpec.parse("initialCapacity=100, maximumSize=500, expireAfterAccess=5m, recordStats");
	}

	public Caffeine<Object, Object> caffeineCacheBuilder() {
		return Caffeine.newBuilder().initialCapacity(100).maximumSize(500).expireAfterAccess(5, TimeUnit.MINUTES)
				.weakKeys().recordStats().removalListener(new CustomRemovalListener());
	}

}

class CustomRemovalListener implements RemovalListener<Object, Object> {

	@Override
	public void onRemoval(@Nullable Object key, @Nullable Object value, @NonNull RemovalCause cause) {
		System.out.format("Removal listener called with key [%s] cause [%s] evicted [%s]\n", key, cause,
				cause.wasEvicted());
	}

}
