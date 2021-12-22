package com.serookie.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.serookie")
public class CosMovieMain9000 {
    public static void main(String[] args) {
        SpringApplication.run(CosMovieMain9000.class,args);
    }
}
