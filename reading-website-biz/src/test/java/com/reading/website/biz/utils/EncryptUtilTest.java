package com.reading.website.biz.utils;

import org.junit.Test;
/**
 * 加密解密工具测试类
 *
 * @xyang010 2019/1/10
 */
public class EncryptUtilTest {

    @Test
    public void testSHA1() {
        EncryptUtil encryptUtil = EncryptUtil.getInstance();
        String res = encryptUtil.SHA1("1234");
        System.out.println(res);
    }
}
