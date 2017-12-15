package com.souro.RedissonJavaBasic;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * Hello world!
 *
 */
public class BasicDemo {
	public static void main(String[] args) {
		Config config = new Config();
		config.useSingleServer().setAddress("http://127.0.0.1:6379");
		RedissonClient client = Redisson.create(config);
	}
}