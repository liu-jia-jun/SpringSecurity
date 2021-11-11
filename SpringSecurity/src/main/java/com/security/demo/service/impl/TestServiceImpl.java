package com.security.demo.service.impl;

import com.security.demo.mapper.UserMapper;
import com.security.demo.pojo.User;
import com.security.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author admin
 */
@Service

public class TestServiceImpl implements TestService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User selectByUsername(String username) {
    return userMapper.selectByUsername(username);

    }
}
