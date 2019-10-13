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
 * ToString时掩码（优先判断secureMask是否安全掩码；如果不是，则根据startSize和endSize进行掩码）
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldFormat(formattedBy = MaskFieldFormatter.class)
public @interface Mask {
    /**
     * 是否安全掩码（true：得到固定{@link MaskFieldFormatter#ALL_MASK_STR_SIZE}位长度的掩码字符串；false：依据startSize和endSize进行掩码）
     */
    boolean secureMask() default false;

    /**
     * 前段明文长度
     */
    int startSize() default 0;

    /**
     * 末段明文长度
     */
    int endSize() default 0;

    /**
     * 掩码字符（默认'*'）
     */
    char maskChar() default '*';
}
