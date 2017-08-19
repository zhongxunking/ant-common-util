/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-24 23:04 创建
 */
package org.antframework.common.util.validation.validator;

import java.util.regex.Pattern;

/**
 * 手机号校验器
 */
public class MobileNoValidator {
    // 手机号正则表达式
    private static final Pattern MOBILE_NO_PATTERN = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|166|17[013678]|18[0-9]|19[8-9])[0-9]{8}$");

    /**
     * 校验
     *
     * @param mobileNo 待校验的手机号
     * @return true 校验通过；false 校验未通过
     */
    public static boolean validate(String mobileNo) {
        if (mobileNo == null) {
            return false;
        }
        return MOBILE_NO_PATTERN.matcher(mobileNo).matches();
    }
}
