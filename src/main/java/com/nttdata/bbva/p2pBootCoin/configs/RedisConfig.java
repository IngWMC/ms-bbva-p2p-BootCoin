package com.nttdata.bbva.p2pBootCoin.configs;

import com.nttdata.bbva.p2pBootCoin.models.RequestBootCoin;
import com.nttdata.bbva.p2pBootCoin.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }
    @Bean
    RedisTemplate<String, User> redisTemplate() {
        final RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }
    @Bean
    RedisTemplate<String, RequestBootCoin> redisTemplateP2P() {
        final RedisTemplate<String, RequestBootCoin> redisTemplateP2P = new RedisTemplate<>();
        redisTemplateP2P.setConnectionFactory(jedisConnectionFactory());
        return redisTemplateP2P;
    }
}
