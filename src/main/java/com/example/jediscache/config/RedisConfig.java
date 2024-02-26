package com.example.jediscache.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

@Configuration
@EnableAutoConfiguration(exclude = {
        JmxAutoConfiguration.class
})
public class RedisConfig {

    @Bean
    public JedisPool createJedisPool() {
        return new JedisPool("127.0.0.1", 6379);

    }
}
