/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-25 13:34 创建
 */
package org.antframework.common.util.validation.validator;

import java.util.regex.Pattern;

/**
 * 邮箱校验器
 */
public class EmailValidator {
    // 邮箱正则表达式
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+([.+-]\\w+)*@\\w+([.-]\\w+)*$");

    /**
     * 校验
     *
     * @param email 带校验的邮箱
     * @return true 校验通过；false 校验未通过
     */
    public static boolean validate(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
