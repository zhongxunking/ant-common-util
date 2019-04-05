/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-20 17:00 创建
 */
package org.antframework.common.util.tostring;

import java.lang.annotation.*;

/**
 * 属性格式化
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldFormat {
    /**
     * 指定格式化器
     */
    Class<? extends FieldFormatter> formattedBy();
}
