/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-22 22:01 创建
 */
package org.antframework.common.util.tostring.format;

import org.antframework.common.util.tostring.FieldFormat;

import java.lang.annotation.*;

/**
 * ToString时掩码
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldFormat(formattedBy = MaskFieldFormatter.class)
public @interface Mask {

    /**
     * 是否全部掩码
     * （true：得到固定长度的掩码字符串；false：程序自动判断需掩码的部分）
     */
    boolean allMask() default false;

    /**
     * 掩码字符（默认'*'）
     */
    char maskChar() default '*';

}
