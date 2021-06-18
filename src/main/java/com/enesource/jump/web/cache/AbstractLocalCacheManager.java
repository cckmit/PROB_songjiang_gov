package com.enesource.jump.web.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author:lio
 * @Description:本地缓存基础类
 * @Date :10:16 上午 2020/7/29
 */
@Slf4j
public abstract class AbstractLocalCacheManager<T> {

    private static Map<String, AbstractLocalCacheManager> map = new HashMap<>();

    private static LoadingCache<String, Optional<Object>> loadingCache = null;

    private static volatile boolean localSwitch = false;

    public AbstractLocalCacheManager() {
        build();
        map.put(this.getClass().getSimpleName(), this);
    }

    private synchronized void build() {
        if (!localSwitch) {
            loadingCache = CacheBuilder
                    .newBuilder()
                    // 多久刷新一次缓存 调用load 方法  目前三分钟刷新一次
                    .refreshAfterWrite(180L, TimeUnit.SECONDS)
                    .build(new CacheLoader<String, Optional<Object>>() {
                        @Override
                        public Optional<Object> load(String key) {
                            return Optional.ofNullable(map.get(key).update());
                        }
                    });
            localSwitch = true;
        }
    }

    /**
     * 更新本地缓存数据
     *
     * @return 子类
     */
    abstract T update();

    /**
     * 格式化本地缓存值
     *
     * @param obj 对象
     * @return 执行
     */
    public T formatCacheValue(Object obj) {
        return (T) obj;
    }


    public T getLocalCache() {
        try {
            return formatCacheValue(loadingCache.get(this.getClass().getSimpleName()).orElse(null));
        } catch (Exception e) {
            log.error("本地缓存获取失败", e);
        }
        return null;
    }
}
