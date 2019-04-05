/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-26 10:27 创建
 */
package org.antframework.common.util.annotation.locate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 位置
 */
@AllArgsConstructor
@Getter
public class Position<A extends Annotation> {
    // 被注解标记的对象
    private final Object target;
    // 被注解标记的字段
    private final Field field;
    // 注解
    private final A annotation;

    /**
     * 获取被注解标记字段的值
     */
    public <T> T getFieldValue() {
        return (T) ReflectionUtils.getField(field, target);
    }

    /**
     * 设置被注解标记的字段的值
     */
    public void setFieldValue(Object newValue) {
        ReflectionUtils.setField(field, target, newValue);
    }
}
