/**
 * 
 */
package com.souro.SpringDataRedisdemo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.souro.SpringDataRedisdemo.service.ResearchService;

/**
 * @author sourabrata
 *
 */

@Configuration
@EnableCaching
@ComponentScan("com.memorynotfound")
@PropertySource("classpath:/redis.properties")
public class AppConfig {

    private @Value("${redis.host}") String redisHost;
    private @Value("${redis.port}") int redisPort;
    
    private static final Map<String, Long> cacheMap = new HashMap<String, Long>();
    static {
        cacheMap.put("souro_cache1", 600L);
        cacheMap.put("souro_cache2", 700L);
        cacheMap.put("souro_cache3", 800L);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisHost);
        factory.setPort(redisPort);
        factory.setUsePool(true);
        factory.setTimeout(100);
        return factory;
    }
    
    @Bean
    RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        /*redisTemplate.setDefaultSerializer(new StringRedisSerializer());*/
        return redisTemplate;
    }

    @Bean
    CacheManager cacheManager() {
    	RedisCacheManager rm= new RedisCacheManager(redisTemplate());
    	rm.setExpires(cacheMap);
        rm.setDefaultExpiration(300);
    	return rm;
    }
    
    @Bean
    ResearchService reserachService(){
    	return new ResearchService();
    }
    
    /*@Bean
    public KeyGenerator keyGenerator() {
      return new KeyGenerator() {
        @Override
        public Object generate(Object o, Method method, Object... objects) {
          // This will generate a unique key of the class name, the method name,
          // and all method parameters appended.
          StringBuilder sb = new StringBuilder();
          sb.append(o.getClass().getName());
          sb.append(method.getName());
          for (Object obj : objects) {
            sb.append(obj.toString());
          }
          return sb.toString();
        }
      };
    }*/
}