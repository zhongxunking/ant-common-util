/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-20 16:24 创建
 */
package org.antframework.common.util.other;

import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 缓存（线程安全）
 *
 * @param <K> 缓存key类型
 * @param <V> 缓存value类型
 */
@AllArgsConstructor
public class Cache<K, V> {
    // null占位符
    private static final Object NULL_VALUE = new Object();

    // 存放缓存的map
    private final Map<K, V> map = new ConcurrentHashMap<>();
    // 缓存提供者
    private final Function<K, ? extends V> supplier;

    /**
     * 获取缓存（如果该缓存不存在，则调用缓存提供者获取缓存）
     *
     * @param key 缓存key
     * @return null 如果缓存提供者提供null
     */
    public V get(K key) {
        V value = map.get(key);
        if (value == null) {
            value = map.computeIfAbsent(key, cacheKey -> toSavable(supplier.apply(cacheKey)));
        }
        return toOriginal(value);
    }

    /**
     * 获取缓存中所有的key
     *
     * @return 缓存中所有的key
     */
    public Set<K> getAllKeys() {
        return map.keySet();
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

    // 转换为可保存value
    private static <V> V toSavable(V original) {
        return original != null ? original : (V) NULL_VALUE;
    }

    // 转换为原始vale
    private static <V> V toOriginal(V savable) {
        return savable != NULL_VALUE ? savable : null;
    }
}
