package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 刘佳俊
 */
@Service
public class UserSserviceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {

        User userDB = userMapper.login(user);
        if (userDB!=null){
            return userDB;
        }else{
            throw new RuntimeException("登录失败");
        }




    }
}
