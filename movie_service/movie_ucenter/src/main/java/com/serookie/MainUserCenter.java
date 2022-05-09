package com.serookie;

import com.github.xiaoymin.swaggerbootstrapui.service.SpringAddtionalModelService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/7
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.serookie.mapper")
public class MainUserCenter {
    public static void main(String[] args) {
        SpringApplication.run(MainUserCenter.class,args);
    }
}
