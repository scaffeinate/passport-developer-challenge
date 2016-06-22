package com.passport.project.factory.redis;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisPublisher {

	@Value("${jedis.host}")
	private String host;

	@Value("${jedis.port}")
	private int port;

	@Value("${jedis.password}")
	private String password;

	private JedisPool jedisPool;
	private Jedis publisherJedis;

	private Logger logger = Logger.getLogger(RedisPublisher.class);

	@PostConstruct
	public void setUp() {
		logger.info("Redis Host: " + host + " Port: " + port);
		this.jedisPool = new JedisPool(host, port);
		this.publisherJedis = jedisPool.getResource();
		if (!password.isEmpty()) {
			this.publisherJedis.auth(password);
		}
	}

	@Async
	public void publishMessage(String channel, String message) {
		publisherJedis.publish(channel, message);
		logger.info("Published to redis");
	}
}
