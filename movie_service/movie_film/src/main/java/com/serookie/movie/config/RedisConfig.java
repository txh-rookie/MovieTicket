package com.serookie.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2021/12/21
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value的序列化 GenericJackson2JsonRedisSerializer不需要传类对象
        // Jackson2JsonRedisSerializer<Object>()需要传对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //hash的序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hash value
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        //注入连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
