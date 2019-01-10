package com.reading.website.biz.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类，用于token生成和验证
 *
 * @xyang010 2019/1/10
 */
@Slf4j
public class JWTUtil {
    // 失效时间
    private static final Long EXPIRE_TIME = 15 * 60 * 1000L;
    // 私钥
    private static final String TOKEN_SECRET = "226093fa-ed40-43c1-b79a-295b80ba21af";

    /**
     * 生成token
     * @param nickName
     * @param userId
     * @return
     */
    public static String sign(String nickName, Long userId) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            //设置私钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            return JWT.create()
                    .withHeader(header)
                    .withClaim("nickName", nickName)
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("create token error, nickName is {}, userId is {}, error is {}", nickName, userId, e);
            return "";
        }
    }

    /**
     * 验证，解析token
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            return JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch (Exception e) {
            log.error("verify token error, token is {}", token, e);
            return null;
        }
    }
}
