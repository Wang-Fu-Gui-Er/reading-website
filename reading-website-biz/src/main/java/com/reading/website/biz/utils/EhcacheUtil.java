package com.reading.website.biz.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Component;

/**
 * 缓存工具类
 *
 * @xyang010 2019/2/26
 */
@Component
public class EhcacheUtil {
    private static final String PATH = "/ehcache.xml";
    private CacheManager manager = CacheManager.create(getClass().getResource(PATH));
    public static final String VERIFY_CODE_CACHE = "verifyCodeCache";

    /**
     * 加入缓存
     *
     * @param cacheName 缓存名称
     * @param key       缓存的键
     * @param value     缓存的值
     */
    public void put(String cacheName, String key, Object value) {
        Cache cache = manager.getCache(cacheName);
        Element element = new Element(key, value);
        cache.put(element);
    }

    /**
     * 从缓存中取数据
     *
     * @param cacheName 缓存名称
     * @param key       缓存的键
     * @return
     */
    public Object get(String cacheName, String key) {
        Cache cache = manager.getCache(cacheName);
        Element element = cache.get(key);
        return element == null ? null : element.getObjectValue();
    }

    /**
     * 获取缓存对象
     *
     * @param cacheName 缓存名称
     * @return
     */
    public Cache get(String cacheName) {
        return manager.getCache(cacheName);
    }

    /**
     * 从缓存中移除
     *
     * @param cacheName 缓存名称
     * @param key       缓存的键
     */
    public void remove(String cacheName, String key) {
        Cache cache = manager.getCache(cacheName);
        cache.remove(key);
    }
}
