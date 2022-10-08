/*
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-10-26 14:42 创建
 */
package org.antframework.common.util.kit;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 属性工具类（从系统属性和系统环境中操作属性）
 */
public final class PropertyUtils {
    /**
     * 获取属性
     *
     * @param key      属性key
     * @param defValue 默认值
     * @return 属性值
     */
    public static String getProperty(String key, String defValue) {
        String value = getProperty(key);
        if (value == null) {
            value = defValue;
        }
        return value;
    }

    /**
     * 获取属性
     *
     * @param key 属性key
     * @return 属性值
     * @throws IllegalArgumentException 如果不存在该属性
     */
    public static String getRequiredProperty(String key) {
        String value = getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("系统属性和系统环境中不存在属性" + key);
        }
        return value;
    }

    /**
     * 获取属性
     *
     * @param key 属性key
     * @return 属性值
     */
    public static String getProperty(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            for (String envKey : toEnvKeys(key)) {
                value = System.getenv(envKey);
                if (value != null) {
                    break;
                }
            }
        }
        return value;
    }

    /**
     * 将属性key转换为env环境中的key
     *
     * @param key 属性key
     * @return env环境中的key
     */
    public static String[] toEnvKeys(String key) {
        Set<String> keys = new LinkedHashSet<>();
        keys.addAll(Arrays.asList(toNoCaseChangeEnvKeys(key)));
        keys.addAll(Arrays.asList(toNoCaseChangeEnvKeys(key.toUpperCase())));

        return keys.toArray(new String[keys.size()]);
    }

    /**
     * 将属性key转换为env环境中的key（大小写不变）
     *
     * @param key 属性key
     * @return env环境中的key
     */
    public static String[] toNoCaseChangeEnvKeys(String key) {
        String noDotKey = key.replace('.', '_');
        String noHyphenKey = key.replace('-', '_');
        String noDotNoHyphenKey = noDotKey.replace('-', '_');

        Set<String> keys = new LinkedHashSet<>();
        keys.add(key);
        keys.add(noDotKey);
        keys.add(noHyphenKey);
        keys.add(noDotNoHyphenKey);
        return keys.toArray(new String[keys.size()]);
    }
}
