package com.reading.website.biz.utils;

import com.reading.website.biz.BaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * 验证码工具测试类
 *
 * @xyang010 2019/2/26
 */
public class VerificationCodeUtilTest extends BaseTest {

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            String result = VerificationCodeUtil.createVerificationCode(4);
            System.out.println("================" + result);
        }
        String result = VerificationCodeUtil.createVerificationCode(4);
        Assert.assertNotNull(result);
    }
}
