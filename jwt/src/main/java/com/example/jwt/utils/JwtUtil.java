package com.example.jwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘佳俊
 */
public class JwtUtil {
    // 设置随机盐
    private static final String SING = "!!!!!!";

    public static String getToken(Map<String, String> map) {
        // 设置过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);

        JWTCreator.Builder builder = JWT.create();

        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });

        // 创建token
        String token = builder
                .withHeader(new HashMap<>())
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SING));
        // 注意这里的签名是根据标头，有效负载和随机盐通过随机盐的算法生成的字符串进行base64算法生成的，

        return token;
    }

    public static DecodedJWT verify(String token){
        // 注意这里的HMAC256算法要和生成token时的算法要一致
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }




}
