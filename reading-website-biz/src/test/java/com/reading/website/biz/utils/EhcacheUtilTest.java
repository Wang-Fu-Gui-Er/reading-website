package com.reading.website.biz.utils;

import com.alibaba.fastjson.JSON;
import com.reading.website.biz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 缓存工具测试类
 *
 * @xyang010 2019/2/26
 */
public class EhcacheUtilTest extends BaseTest {

    @Autowired
    private EhcacheUtil cache;

    @Test
    public void test1() {
        cache.put("testCache", "userId", "123");
        Object object = cache.get("testCache", "userId");
    }
}
