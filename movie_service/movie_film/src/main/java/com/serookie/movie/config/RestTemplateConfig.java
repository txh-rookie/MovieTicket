package com.serookie.movie.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2021/12/25
 */
@Configuration
public class RestTemplateConfig {

    @LoadBalanced//开启负载均衡
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
