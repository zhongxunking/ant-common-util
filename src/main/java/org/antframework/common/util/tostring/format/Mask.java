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
 * （优先判断allMask是否全部掩码，如果不是，则判断startSize、endSize，如果这两个属性没被设置，则会自动判断需掩码部分{@link MaskFieldFormatter}）
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldFormat(formattedBy = MaskFieldFormatter.class)
public @interface Mask {
    /**
     * 是否全部掩码
     * （true：得到固定{@link MaskFieldFormatter#ALL_MASK_STR_SIZE}位长度的掩码字符串；false：依据下面其他属性进行掩码）
     */
    boolean allMask() default false;

    /**
     * 前段明文长度
     * （默认不生效。如果本属性被设置，则endSize也必须被设置）
     */
    int startSize() default -1;

    /**
     * 末段明文长度
     * （默认不生效。如果本属性被设置，则startSize也必须被设置）
     */
    int endSize() default -1;

    /**
     * 掩码字符（默认'*'）
     */
    char maskChar() default '*';
}
