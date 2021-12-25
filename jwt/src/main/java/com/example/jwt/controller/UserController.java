package com.example.jwt.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwt.entity.User;
import com.example.jwt.service.UserService;
import com.example.jwt.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘佳俊
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        log.info("用户名：[{}]", user.getUsername());
        log.info("密码：[{}]", user.getPassword());
        HashMap<String, Object> map = new HashMap<>();
        try {
            User userDB = userService.login(user);
            HashMap<String, String> payload = new HashMap<>();
            payload.put("id", userDB.getId());
            payload.put("username", userDB.getUsername());
            String token = JwtUtil.getToken(payload);
            map.put("state", true);
            map.put("msg", "认证成功");
            map.put("token", token);// 响应token，返回给前端浏览器
        } catch (Exception e) {
            map.put("state", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }


    @GetMapping("test")
    public Map<String,Object> test(String token){
        HashMap<String, Object> map = new HashMap<>();
        log.info("当前token：[{}]",token);
        try{
            DecodedJWT verify = JwtUtil.verify(token);// 验证令牌
            map.put("state",true);
            map.put("msg","请求成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","请求失败");

        }
        return map;
    }


}
