/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-13 17:38 创建
 */
package org.antframework.common.util.file;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Ignore
public class MapFileTest {
    private MapFile mapFile = new MapFile(System.getProperty("user.home") + "/aa/bb.properties");

    @Test
    public void testReadAll() {
        Map<String, String> map = mapFile.readAll();
    }

    @Test
    public void testRead() {
        String value = mapFile.read("aaa");
    }

    @Test
    public void testStoreAll() {
        Map<String, String> map = new HashMap<>();
        map.put("aaa", "001");
        map.put("bbb", "002");
        map.put("ccc", null);

        mapFile.storeAll(map);
    }

    @Test
    public void testStore() {
        String oldValue = mapFile.store("bbb", "005");
    }
}
