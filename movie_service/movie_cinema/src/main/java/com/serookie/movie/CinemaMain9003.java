package com.serookie.movie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2021/12/25
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients //启动服务调用 openFeign
@EnableTransactionManagement//开启事务支持
public class CinemaMain9003 {
    public static void main(String[] args) {
        SpringApplication.run(CinemaMain9003.class,args);
    }
}
