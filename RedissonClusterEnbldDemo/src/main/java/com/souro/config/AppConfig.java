/**
 * 
 */
package com.souro.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.souro.controller.ResearchControllerDemo;
import com.souro.errorhandler.CustomCacheErrorHandler;
import com.souro.service.ResearchService;

/**
 * @author sourabrata
 *
 */

@Configuration
@EnableCaching
@ComponentScan("com.souro")
@PropertySource("classpath:/redis.properties")
public class AppConfig extends CachingConfigurerSupport {

	private @Value("${redis.host}") String redisHost;
	private @Value("${redis.port}") int redisPort;
	private @Value("${ttl.expire}") long ttlExpire;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ViewResolver configureViewResolver() {
		InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
		viewResolve.setPrefix("/WEB-INF/jsp/");
		viewResolve.setSuffix(".jsp");
		return viewResolve;
	}

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		String [] nodeList = new String[1];
		nodeList[0] = "redis://"+redisHost+":"+redisPort;
		config.useClusterServers().addNodeAddress(nodeList).setLoadBalancer(new RoundRobinLoadBalancer());
		RedissonClient redisson = Redisson.create(config);
		return redisson;
	}

	@Bean
	public CacheConfig cacheConfig() {
		CacheConfig cacheConfig = new CacheConfig();
		cacheConfig.setTTL(500000);
		return cacheConfig;
	}

	@Bean
	public RedissonSpringCacheManager redissonSpringCacheManager() {
		Map<String, CacheConfig> configMap = new ConcurrentHashMap<String, CacheConfig>();
		configMap.put("Souro_MSCache1", cacheConfig());
		configMap.put("Souro_MSCache2", cacheConfig());
		RedissonSpringCacheManager redissonSpringCacheManager = new RedissonSpringCacheManager(
				redissonClient(), configMap);
		return redissonSpringCacheManager;
	}

	@Bean
	ResearchService reserachService() {
		return new ResearchService();
	}

	@Bean
	ResearchControllerDemo researchControllerDemo() {
		ResearchControllerDemo researchControllerDemo = new ResearchControllerDemo();
		return researchControllerDemo;
	}

	@Override
	@Bean
	public CacheErrorHandler errorHandler() {
		return new CustomCacheErrorHandler();
	}
}