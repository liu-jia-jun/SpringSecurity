package com.example.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashMap;

/**
 * @author 刘佳俊
 */
public class TestJwt {

    @Test
    void jwtTest(){

        // 设置过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,60*60);

        // 创建token
        String token = JWT.create()
                .withHeader(new HashMap<>())// 设置标头
                .withClaim("userID", 00001)
                .withClaim("username", "admin")// 设置有效负载，即携带数据
                .withExpiresAt(instance.getTime())// 设置过期时间
                .sign(Algorithm.HMAC256("!!!!!!"));// 设置签名的随机盐和随即盐的算法，
                // 注意这里的签名是根据标头，有效负载和随机盐通过随机盐的算法生成的字符串进行base64算法生成的，
        System.out.println(token);

/**
 * 注意当你设置了过期时间，那么每次生成的token的携带关于过期时间的信息
 */

    }


    @Test
    void test(){
        // 创建验证对象，注意这里创建的对象给定的"!!!!!!" 是数据库中的随机盐
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!!!!!!")).build();


        // verify是验证对象根据token字符串进行解码生成的对象，verify对象可以得到未加密时的数据
        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDA0Mjc0NjAsInVzZXJJRCI6MSwidXNlcm5hbWUiOiJhZG1pbiJ9.IG_GXvlc7AHVAvMhVgTyt32o4DuBnarvG3djcl0hSxs");

        System.out.println("用户ID++++"+verify.getClaim("userID").asInt());
        System.out.println("用户名++++"+verify.getClaim("username").asString());
        System.out.println("过期时间++++"+verify.getExpiresAt());



    }



























}
