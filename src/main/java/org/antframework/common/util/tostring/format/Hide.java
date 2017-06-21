/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-20 18:19 创建
 */
package org.antframework.common.util.tostring.format;

import org.antframework.common.util.tostring.FieldFormat;

import java.lang.annotation.*;

/**
 * ToString时隐藏被标注的字段
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldFormat(formattedBy = HideFieldFormatter.class)
public @interface Hide {
}
