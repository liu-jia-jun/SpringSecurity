package com.security.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 设置SpringSecurity的用户名和密码的第二种方式
 *
 * 编写一个Config类去继承WebSecurityConfigurerAdapter类并重写里面的configure方法
 *
 * @author admin
 */
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("123");
        auth.inMemoryAuthentication().passwordEncoder(bCryptPasswordEncoder).withUser("liu").password(password).roles("admin");
    }
}
