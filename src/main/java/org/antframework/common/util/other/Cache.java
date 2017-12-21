/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-20 16:24 创建
 */
package org.antframework.common.util.other;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存（线程安全）
 *
 * @param <K> 缓存key类型
 * @param <V> 缓存value类型
 */
public class Cache<K, V> {
    // 存放缓存的map
    private final Map<K, V> map = new ConcurrentHashMap<>();
    // 缓存提供者
    private final Supplier<K, ? extends V> supplier;

    /**
     * 创建新的缓存
     *
     * @param supplier 缓存value提供者
     */
    public Cache(Supplier<K, ? extends V> supplier) {
        this.supplier = supplier;
    }

    /**
     * 获取缓存（如果该缓存不存在，则调用缓存提供者获取缓存）
     *
     * @param key 缓存key
     * @return null 如果缓存不存在且缓存提供者也不提供该缓存
     */
    public V get(K key) {
        V value = map.get(key);
        if (value == null) {
            synchronized (map) {
                value = map.get(key);
                if (value == null) {
                    value = supplier.get(key);
                    if (value != null) {
                        map.put(key, value);
                    }
                }
            }
        }
        return value;
    }

    /**
     * 获取所有缓存
     *
     * @return 包含所有缓存的不可修改map
     */
    public Map<K, V> getAll() {
        return Collections.unmodifiableMap(map);
    }

    /**
     * 删除缓存
     *
     * @param key 缓存key
     * @return 被删除的缓存value（如果不存在该缓存则返回null）
     */
    public V remove(K key) {
        return map.remove(key);
    }

    /**
     * 清除所有缓存
     */
    public void clear() {
        map.clear();
    }

    /**
     * 缓存提供者
     *
     * @param <K> 缓存key类型
     * @param <V> 缓存value类型
     */
    public interface Supplier<K, V> {

        /**
         * 获取缓存value
         *
         * @param key 缓存key
         * @return null 如果不能提供该缓存
         */
        V get(K key);
    }
}
