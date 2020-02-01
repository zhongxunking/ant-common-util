/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-22 22:02 创建
 */
package org.antframework.common.util.tostring.format;

import org.antframework.common.util.other.Cache;
import org.antframework.common.util.tostring.FieldFormatter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 掩码格式器
 */
public class MaskFieldFormatter implements FieldFormatter {
    // 全部掩码的字符长度
    private static final int ALL_MASK_STR_SIZE = 6;
    // 安全掩码缓存
    private static final Cache<Character, String> SECURE_MASK_CACHE = new Cache<>(MaskFieldFormatter::buildSecureMaskedStr);

    // 需被掩码的属性
    private Field field;
    // 被格式化的属性前段（属性名=）
    private String formattedPre;
    // 是否安全掩码
    private boolean secureMask;
    // 前段明文长度
    private int startSize;
    // 末段明文长度
    private int endSize;
    // 掩码字符
    private char maskChar;

    @Override
    public void initialize(Field field) {
        Mask maskAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, Mask.class);
        Assert.isAssignable(CharSequence.class, field.getType(), String.format("@Mask只能标注在CharSequence类型字段上，字段[%s]不是CharSequence类型", field));
        Assert.isTrue(maskAnnotation.startSize() >= 0 && maskAnnotation.endSize() >= 0, String.format("属性[%s]的@Mask注解设置不合法：startSize、endSize不能小于0", field));
        this.field = field;
        formattedPre = field.getName() + "=";
        secureMask = maskAnnotation.secureMask();
        startSize = maskAnnotation.startSize();
        endSize = maskAnnotation.endSize();
        maskChar = maskAnnotation.maskChar();
    }

    @Override
    public String format(Object obj) {
        String maskedStr = null;
        String str = (String) ReflectionUtils.getField(field, obj);
        if (str != null) {
            if (secureMask) {
                maskedStr = SECURE_MASK_CACHE.get(maskChar);
            } else {
                maskedStr = MaskUtils.mask(str, startSize, endSize, maskChar);
            }
        }

        return formattedPre + maskedStr;
    }

    // 构建固定长度的安全掩码字符串
    private static String buildSecureMaskedStr(Character maskChar) {
        StringBuilder builder = new StringBuilder(ALL_MASK_STR_SIZE);
        for (int i = 0; i < ALL_MASK_STR_SIZE; i++) {
            builder.append(maskChar);
        }
        return builder.toString();
    }
}
