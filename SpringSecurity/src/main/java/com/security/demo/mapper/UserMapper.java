package com.security.demo.mapper;

import com.security.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 */
@Repository
@Mapper
public interface UserMapper {
    User selectByUsername(@Param("username") String username);
}
