package com.shadoumaimall.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shadoumaimall.boot.mapper")
public class ShadoumaimallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShadoumaimallApplication.class, args);
    }

}
