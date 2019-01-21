package com.reading.website.biz.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.UUID;

/**
 * class comment
 *
 * @xyang010 2019/1/10
 */
public class JWTUtilTest {

    @Test
    public void testSign() {
        String nickName = "test";
        Long userId = 123L;
        String token = JWTUtil.sign(nickName, userId);
        System.out.println(token);
        DecodedJWT decodedJWT = JWTUtil.verify(token);
        System.out.println(JSON.toJSON(decodedJWT));
    }

    @Test
    public void testVerify() {
        String tokenSecret  = UUID.randomUUID().toString();
        System.out.println(tokenSecret);
    }
}
