package com.example.jwt.mapper;

import com.example.jwt.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 刘佳俊
 */
@Mapper
public interface UserMapper {

    User login(User user);
}
