/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-20 17:25 创建
 */
package org.antframework.common.util.other;

import org.junit.Assert;
import org.junit.Test;

/**
 * 缓存单元测试
 */
public class CacheTest {
    private Cache<String, Integer> cache = new Cache<>(new Cache.Supplier<String, Integer>() {
        @Override
        public Integer get(String key) {
            Integer value = Integer.parseInt(key);
            if (value > 100) {
                return null;
            }
            return value;
        }
    });

    @Test
    public void testGet() {
        for (int i = 0; i < 100; i++) {
            int value = cache.get(Integer.toString(i));
            Assert.assertEquals(i, value);
        }
        for (int i = 0; i < 100; i++) {
            int value = cache.get(Integer.toString(i));
            Assert.assertEquals(i, value);
        }

        Integer value1 = cache.get(Integer.toString(101));
        Assert.assertEquals(null, value1);
        Integer value2 = cache.get(Integer.toString(101));
        Assert.assertEquals(null, value2);
    }
}
