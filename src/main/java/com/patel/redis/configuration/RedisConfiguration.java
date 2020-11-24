package com.patel.redis.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisConfiguration extends CachingConfigurerSupport {

	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private int redisPort;
	@Value("${spring.redis.master}")
	private String redisMaste;
	@Value("${spring.redis.nodes}")
	private String redisNodes;
	@Value("${redis.sentinel.mode}")
	private boolean redisMode;
	@Value("${spring.redis.jedis.pool.max-active:5000}")
	private int maxActiveConn;
	@Value("${spring.redis.jedis.pool.max-idle:8}")
	private int maxIdle;

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(connectionFactory());
		redisTemplate.setStringSerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		return redisTemplate;
	}

	@Bean
	public JedisConnectionFactory connectionFactory() {
		List<String> nodes = Arrays.asList(redisNodes.split(","));
		if (nodes.size() > 1) {
			return new JedisConnectionFactory(new RedisClusterConfiguration(nodes));
		} else {
			RedisStandaloneConfiguration redisStandalonseConfiguration = new RedisStandaloneConfiguration(nodes.get(0),
					redisPort);
			return new JedisConnectionFactory(redisStandalonseConfiguration);
		}
	}
}
