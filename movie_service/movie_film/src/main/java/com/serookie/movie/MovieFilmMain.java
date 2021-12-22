package com.serookie.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.serookie")
public class MovieFilmMain {
    public static void main(String[] args) {
        SpringApplication.run(MovieFilmMain.class,args);
    }
}
