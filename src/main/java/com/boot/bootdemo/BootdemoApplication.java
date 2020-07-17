package com.boot.bootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@MapperScan("com.boot.bootdemo.dao")
@SpringBootApplication
public class BootdemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(BootdemoApplication.class, args);
    }

}
