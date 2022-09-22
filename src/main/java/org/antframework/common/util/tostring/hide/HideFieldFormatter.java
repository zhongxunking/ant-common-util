/*
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-20 18:21 创建
 */
package org.antframework.common.util.tostring.hide;

import org.antframework.common.util.tostring.FieldFormatter;

import java.lang.reflect.Field;

/**
 * 隐藏属性的格式器（不输出属性）
 */
public class HideFieldFormatter implements FieldFormatter {
    @Override
    public void init(Field field) {
    }

    @Override
    public String format(Object obj) {
        return null;
    }
}
