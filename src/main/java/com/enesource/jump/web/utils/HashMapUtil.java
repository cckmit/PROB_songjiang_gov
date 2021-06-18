package com.enesource.jump.web.utils;

import com.google.common.collect.Maps;

import java.util.Map;

/* *
 * @author lio
 * @Description:
 * @date :created in 3:19 下午 2021/2/18
 */
public class HashMapUtil {


    private static Map<String, String> cacheMap = Maps.newHashMap();

    /**
     * 获取缓存的对象
     *
     * @param account
     * @return
     */
    public static String getCache(String account) {
        account = getCacheKey(account);
        // 如果缓冲中有该账号，则返回value
        if (cacheMap.containsKey(account)) {
            return cacheMap.get(account);
        }
        return cacheMap.get(account);
    }


    /**
     * 拼接一个缓存key
     *
     * @param account
     * @return
     */
    private static String getCacheKey(String account) {
        return Thread.currentThread().getId() + "-" + account;
    }

    /**
     * 移除缓存信息
     *
     * @param account
     */
    public static void removeCache(String account) {
        cacheMap.remove(getCacheKey(account));
    }
}
