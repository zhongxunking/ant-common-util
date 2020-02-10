/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-20 17:01 创建
 */
package org.antframework.common.util.tostring;

import java.lang.reflect.Field;

/**
 * 属性格式化器（实现类必须提供默认构造函数）
 */
public interface FieldFormatter {
    /**
     * 初始化（一个格式化器实例只被执行一次）
     *
     * @param field 被标注的属性
     */
    void init(Field field);

    /**
     * 格式化（此方法会被并发调用，实现类需保证线程安全）
     *
     * @param obj 被ToString的对象
     * @return 属性格式化后的字符串（一般是name="abc"这种格式），返回null表示此属性被隐藏
     */
    String format(Object obj);
}
