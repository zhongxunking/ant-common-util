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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
     * 读取value
     *
     * @param key 读取的key
     * @return key对应的value（如果文件不存在或不存在该key，则返回null）
     */
    public String read(String key) {
        Map<String, String> map = readAll();
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    /**
     * 读取整个map
     *
     * @return map（如果文件不存在，则返回null）
     */
    public Map<String, String> readAll() {
        if (!file.exists()) {
            return null;
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
     * @return key对应的旧value（如果文件不存在或不存在旧value，则返回null）
     */
    public synchronized String store(String key, String value) {
        Map<String, String> map = readAll();
        if (map == null) {
            map = new HashMap<>();
        }
        String oldValue = map.put(key, value);
        storeAll(map);
        return oldValue;
    }

    /**
     * 存储整个map
     *
     * @param map 被存储的map
     */
    public synchronized void storeAll(Map<String, String> map) {
        // 如果文件不存在，则创建
        FileUtils.createFileIfAbsent(file.getPath());
        try {
            OutputStream out = null;
            try {
                out = new FileOutputStream(file);
                mapToProps(map).store(out, "updated at " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } catch (IOException e) {
            ExceptionUtils.rethrow(e);
        }
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
