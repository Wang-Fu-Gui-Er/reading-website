package com.reading.website.biz.utils;

import java.io.Serializable;
import java.util.Random;

/**
 * 验证码工具类
 *
 * @xyang010 2019/2/26
 */
public class VerificationCodeUtil implements Serializable {

    /**
     * 生成验证码，默认6位
     * @return
     */
    public static String createVerificationCode() {
        return createVerificationCode(6);
    }

    /**
     * 生成指定位数验证码
     * @param n 验证码位数
     * @return
     */
    public static String createVerificationCode(int n) {
        //保存数字0-9 和 大小写字母
        String candidateSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        //声明一个字符数组对象ch 保存 验证码
        char[] verificationCode = new char[n];
        for (int i = 0; i < n; i++) {
            //创建一个新的随机数生成器
            Random random = new Random();
            //返回[0,string.length)范围的int值    作用：保存下标
            int index = random.nextInt(candidateSet.length());
            //charAt() : 返回指定索引处的 char 值   ==》保存到字符数组对象ch里面
            verificationCode[i] = candidateSet.charAt(index);
        }
        return new String(verificationCode);
    }
}
