/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-13 16:25 创建
 */
package org.antframework.common.util.file;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.*;
import java.util.*;

/**
 * map文件
 */
public class MapFile {
    // value为null时存入文件的占位符
    private static final String NULL_VALUE = MapFile.class.getName() + "#NULL_VALUE";
    // 文件
    private File file;

    /**
     * 新建map文件
     *
     * @param filePath 文件路径
     */
    public MapFile(String filePath) {
        file = new File(filePath);
    }

    /**
     * 文件是否存在
     */
    public boolean exists() {
        return file.exists();
    }

    /**
     * 读取value
     *
     * @param key 读取的key
     * @return key对应的value（如果文件不存在或不存在该key或对应的value为null，则返回null）
     */
    public String read(String key) {
        return readAll().get(key);
    }

    /**
     * 读取整个map
     *
     * @return map（如果文件不存在，则返回空map）
     */
    public Map<String, String> readAll() {
        if (!exists()) {
            return new HashMap<>();
        }
        try {
            InputStream in = null;
            try {
                in = new FileInputStream(file);
                Properties props = new Properties();
                props.load(in);
                return propsToMap(props);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (IOException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    /**
     * 存储key-value
     *
     * @param key   被存储的key
     * @param value 被存储的value
     */
    public synchronized void store(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        storeAll(map);
    }

    /**
     * 存储整个map
     *
     * @param map 被存储的map
     */
    public synchronized void storeAll(Map<String, String> map) {
        Map<String, String> newMap = readAll();
        newMap.putAll(map);
        replace(newMap);
    }

    /**
     * 以新map替换整个旧map
     *
     * @param newMap 新map
     */
    public synchronized void replace(Map<String, String> newMap) {
        // 如果文件不存在，则创建
        FileUtils.createFileIfAbsent(file.getPath());
        try {
            OutputStream out = null;
            try {
                out = new FileOutputStream(file);
                mapToProps(newMap).store(out, "updated at " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } catch (IOException e) {
            ExceptionUtils.rethrow(e);
        }
    }

    /**
     * 删除指定key
     *
     * @param key 需被删除的key
     */
    public synchronized void remove(String key) {
        Map<String, String> newMap = readAll();
        newMap.remove(key);
        replace(newMap);
    }

    /**
     * 清除整个map
     */
    public synchronized void clear() {
        replace(Collections.EMPTY_MAP);
    }

    // Properties转Map
    private static Map<String, String> propsToMap(Properties props) {
        Map<String, String> map = new HashMap<>();
        for (String key : props.stringPropertyNames()) {
            String value = props.getProperty(key);
            if (StringUtils.equals(value, NULL_VALUE)) {
                value = null;
            }
            map.put(key, value);
        }
        return map;
    }

    // Map转Properties
    private static Properties mapToProps(Map<String, String> map) {
        Properties props = new Properties();
        for (String key : map.keySet()) {
            String value = map.get(key);
            if (value == null) {
                value = NULL_VALUE;
            }
            props.setProperty(key, value);
        }
        return props;
    }
}
