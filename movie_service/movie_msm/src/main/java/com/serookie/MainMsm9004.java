package com.serookie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/4
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//启动的时候不加载数据库
@EnableDiscoveryClient//开启服务调用 openfeign
public class MainMsm9004 {
    public static void main(String[] args) {
        SpringApplication.run(MainMsm9004.class,args);
    }
}
