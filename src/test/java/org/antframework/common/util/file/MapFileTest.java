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
    public void testGetFilePath() {
        String filePath = mapFile.getFilePath();
    }

    @Test
    public void testExists() {
        boolean exists = mapFile.exists();
    }

    @Test
    public void testRead() {
        String value = mapFile.read("aaa");
    }

    @Test
    public void testReadAll() {
        Map<String, String> map = mapFile.readAll();
    }

    @Test
    public void testStore() {
        mapFile.store("aaa", "001");
    }

    @Test
    public void testStoreAll() {
        Map<String, String> map = new HashMap<>();
        map.put("aaa", "101");
        map.put("bbb", "102");
        map.put("ccc", null);

        mapFile.storeAll(map);
    }

    @Test
    public void testReplace() {
        Map<String, String> newMap = new HashMap<>();
        newMap.put("aaa", "001");
        newMap.put("ccc", null);
        mapFile.replace(newMap);
    }

    @Test
    public void testRemove() {
        mapFile.remove("aaa");
    }

    @Test
    public void testClear() {
        mapFile.clear();
    }
}
