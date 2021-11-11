package com.security.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@MapperScan(basePackages = {"com.security.demo.mapper"})
@ComponentScan(basePackages = {"com.security"})

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//该注解的作用是开启Springsecurity权限授权的注解
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
