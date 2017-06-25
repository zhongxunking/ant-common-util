/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-25 15:57 创建
 */
package org.antframework.common.util.validation.validator;

import java.util.regex.Pattern;

/**
 * 组织机构代码校验器
 */
public class OrganizationCodeValidator {
    // 组织机构代码正则表达式
    private static Pattern ORGANIZATION_CODE_PATTERN = Pattern.compile("^[a-zA-z0-9\\-]{8,}[A-Z0-9]$");

    /**
     * 校验
     *
     * @param organizationCode 待校验组织机构代码
     * @return true 校验通过；false 校验未通过
     */
    public static boolean validate(String organizationCode) {
        if (organizationCode == null) {
            return false;
        }
        return ORGANIZATION_CODE_PATTERN.matcher(organizationCode).matches();
    }
}
