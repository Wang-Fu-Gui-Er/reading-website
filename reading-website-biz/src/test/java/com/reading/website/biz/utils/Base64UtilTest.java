package com.reading.website.biz.utils;

import com.reading.website.biz.BaseTest;
import org.junit.Test;

/**
 * Base64UtilTest
 *
 * @xyang010 2019/4/8
 */
public class Base64UtilTest extends BaseTest {

    @Test
    public void test() {
        String sourceStr = "pGSxSN0AA\rDijAAhj";
        String targetStr = sourceStr.replaceAll("\r", "");
        System.out.println(targetStr);
    }
}
