package com.serookie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/27
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MainGateway {
    public static void main(String[] args) {
        SpringApplication.run(MainGateway.class,args);
    }
}
