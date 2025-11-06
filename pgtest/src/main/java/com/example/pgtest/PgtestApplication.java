package com.example.pgtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PgtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PgtestApplication.class, args);
    }

}
