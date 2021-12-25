package com.example.jwt.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 刘佳俊
 */
@Data
@Accessors(chain = true)
public class User {
    private String id;
    private String username;
    private String password;
}
