package com.security.demo.service.impl;

import com.security.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 第三种方式设置用户名名和密码
 *
 * 创建一个类去实现UserDetailsService接口,
 * 并实现loadUserByUsername(String s)
 *
 * 在loadUserByUsername方法中
 * 通过获取前端页面表单中传过来的username的值来在数据库中查询出这个用户的信息包括权限
 *
 *
 *
 * @author admin
 */
@Service("userDetailsService")

public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {


        System.out.println("用户名"+s);
         com.security.demo.pojo.User user = userMapper.selectByUsername(s);
        List<GrantedAuthority> auths= null;
        List<GrantedAuthority> auths1= AuthorityUtils.
                commaSeparatedStringToAuthorityList("admin");
        List<GrantedAuthority> auths2= AuthorityUtils.
                commaSeparatedStringToAuthorityList("worker");

        System.out.println("auths======"+auths);
        System.out.println("auths1======"+auths1);
        System.out.println("auths2======"+auths2);

        if (user==null){
            throw new UsernameNotFoundException("该用户不存在");
        }
        System.out.println("username====="+user.getUsername());
        if("张三".equals(user.getUsername())){
            auths=auths1;
        }
        if("李四".equals(user.getUsername())){
            auths=auths2;
        }
        System.out.println(auths);



        return new User(user.getUsername(),
                new BCryptPasswordEncoder().encode(user.getPassword()),auths);
    }


}
